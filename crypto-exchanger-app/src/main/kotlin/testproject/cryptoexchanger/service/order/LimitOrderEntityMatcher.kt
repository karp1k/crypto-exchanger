package testproject.cryptoexchanger.service.order

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import testproject.cryptoexchanger.enumeration.OrderDirection
import testproject.cryptoexchanger.enumeration.OrderStatus
import testproject.cryptoexchanger.enumeration.TransactionStatus
import testproject.cryptoexchanger.enumeration.TransactionType
import testproject.cryptoexchanger.model.Account
import testproject.cryptoexchanger.model.OrderEntity
import testproject.cryptoexchanger.repository.OrderRepository
import testproject.cryptoexchanger.service.SimpleTransferService
import testproject.cryptoexchanger.service.transaction.TransactionFactory
import testproject.cryptoexchanger.service.transaction.TransactionService
import java.math.BigDecimal

@Component
class LimitOrderEntityMatcher(
    private val orderRepository: OrderRepository,
    private val simpleTransferService: SimpleTransferService,
    private val transactionService: TransactionService,
) : OrderEntityMatcher {

    @Transactional
    override fun match(bid: OrderEntity, ask: OrderEntity) {

        val matchedQuantity = bid.getRemainingQuantity().min(ask.getRemainingQuantity())
        val tradePrice = ask.price // determine the price at which the order will be processed
        val totalCost = tradePrice.multiply(matchedQuantity)

        bid.quantityFulfilled = bid.quantityFulfilled.add(matchedQuantity)
        bid.hold = bid.hold.subtract(totalCost)

        ask.quantityFulfilled = ask.quantityFulfilled.add(totalCost)
        ask.hold = ask.hold.subtract(matchedQuantity)

        handelOrderFulfillment(ask)
        handelOrderFulfillment(bid)
        TransactionFactory.createOrderTradeTransaction(bid, ask, totalCost, matchedQuantity).also {
            transactionService.createOrUpdate(it)
        }
    }

    private fun handelOrderFulfillment(orderEntity: OrderEntity) {
        if (isOrderEntityFulfilled(orderEntity)) {
            orderEntity.status = OrderStatus.FULFILLED
            depositHoldAndReturnRemainings(orderEntity)
        }
        orderRepository.save(orderEntity)
    }

    private fun isOrderEntityFulfilled(orderEntity: OrderEntity): Boolean {
        return if (OrderDirection.BUY == orderEntity.direction) {
            orderEntity.quantity == orderEntity.quantityFulfilled
        } else {
            orderEntity.hold.signum() == 0
        }
    }

    private fun depositHoldAndReturnRemainings(orderEntity: OrderEntity) {
        if (OrderDirection.BUY == orderEntity.direction) {
            if (!BigDecimal.ZERO.equals(orderEntity.hold)) {
                simpleTransferService.deposit(orderEntity.quoteAccount.id!!, orderEntity.hold)
                createDepositTransaction(orderEntity.quoteAccount, orderEntity.hold, TransactionType.ORDER_HOLD_RETURN)
            }
            simpleTransferService.deposit(orderEntity.baseAccount.id!!, orderEntity.quantityFulfilled)
            createDepositTransaction(orderEntity.baseAccount,
                orderEntity.quantityFulfilled,
                TransactionType.ORDER_FULFILMENT)
        } else {
            simpleTransferService.deposit(orderEntity.quoteAccount.id!!, orderEntity.quantityFulfilled)
            createDepositTransaction(orderEntity.quoteAccount,
                orderEntity.quantityFulfilled,
                TransactionType.ORDER_FULFILMENT)
        }
    }

    private fun createDepositTransaction(toAccount: Account, amount: BigDecimal, transactionType: TransactionType) {
        TransactionFactory.createDepositTransaction(toAccount,
            amount,
            toAccount.currency,
            TransactionStatus.FINISHED,
            transactionType).also { transactionService.createOrUpdate(it) }
    }
}
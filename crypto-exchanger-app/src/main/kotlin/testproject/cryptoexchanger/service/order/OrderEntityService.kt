package testproject.cryptoexchanger.service.order

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import testproject.cryptoexchanger.dto.CreateOrderEntityDto
import testproject.cryptoexchanger.dto.OrderBookDto
import testproject.cryptoexchanger.dto.OrderEntityDto
import testproject.cryptoexchanger.enumeration.OrderDirection
import testproject.cryptoexchanger.enumeration.OrderStatus
import testproject.cryptoexchanger.enumeration.TransactionStatus
import testproject.cryptoexchanger.enumeration.TransactionType
import testproject.cryptoexchanger.mapper.OrderMapper
import testproject.cryptoexchanger.model.Account
import testproject.cryptoexchanger.repository.AccountRepository
import testproject.cryptoexchanger.repository.OrderRepository
import testproject.cryptoexchanger.service.TransferService
import testproject.cryptoexchanger.service.transaction.TransactionFactory
import testproject.cryptoexchanger.service.transaction.TransactionService
import java.math.BigDecimal

@Service
class OrderEntityService(
    private val orderRepository: OrderRepository,
    private val accountRepository: AccountRepository,
    private val transferService: TransferService,
    private val transactionService: TransactionService,
) {

    private val orderValidator = OrderValidator()

    @Transactional
    fun placeOrder(dto: CreateOrderEntityDto, username: String) {
        orderValidator.validateNewOrder(dto)
        val baseAccount =
            accountRepository.findByWalletAddressIdAndCurrencyCodeAndUsername(dto.walletAddressId,
                dto.baseCurrencyCode,
                username)
        val quoteAccount =
            accountRepository.findByWalletAddressIdAndCurrencyCodeAndUsername(dto.walletAddressId,
                dto.quoteCurrencyCode,
                username)
        val holdAccountAndAmount = getHoldAccountIdAndAmount(dto, baseAccount, quoteAccount)
        transferService.withdraw(holdAccountAndAmount.first.id!!, holdAccountAndAmount.second)
        orderRepository.save(OrderMapper.toEntity(dto,
            baseAccount,
            quoteAccount,
            holdAccountAndAmount.second,
            baseAccount.walletAddress.wallet.userEntity))
        TransactionFactory.createWithdrawTransaction(
            holdAccountAndAmount.first,
            holdAccountAndAmount.second,
            holdAccountAndAmount.first.currency,
            TransactionStatus.FINISHED,
            TransactionType.ORDER_HOLD
        ).also { transactionService.createOrUpdate(it) }
    }

    private fun getHoldAccountIdAndAmount(
        dto: CreateOrderEntityDto,
        baseAccount: Account,
        quoteAccount: Account,
    ): Pair<Account, BigDecimal> =
        if (OrderDirection.BUY == dto.direction) {
            Pair(quoteAccount, dto.quantity.multiply(dto.price))
        } else {
            Pair(baseAccount, dto.quantity)
        }

    fun getAllByUsername(username: String): Set<OrderEntityDto> {
        return orderRepository.findAllByUsername(username).mapTo(HashSet()) { entity -> OrderMapper.toDto(entity) }
    }

    fun getAllActive(): Set<OrderBookDto> {
        return orderRepository.findAllByStatus(OrderStatus.PENDING)
            .mapTo(HashSet()) { entity -> OrderMapper.toOrderBookDto(entity) }
    }


}
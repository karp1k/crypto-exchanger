package testproject.cryptoexchanger.service.order

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import testproject.cryptoexchanger.enumeration.OrderDirection
import testproject.cryptoexchanger.enumeration.OrderStatus
import testproject.cryptoexchanger.repository.OrderRepository

@Component
class ScheduledOrderEntityMatchingHandler(
    private val limitOrderEntityMatcher: OrderEntityMatcher,
    private val orderRepository: OrderRepository,
) {


    /**
     * handle matching orders in FIFO style
     */
    @Scheduled(cron = "\${app.order-matching-handler.cron}")
    fun handleOrderMatcher() {
        val bids =
            orderRepository.findAllByDirectionAndStatusOrderByCreatedAtDesc(OrderDirection.BUY, OrderStatus.PENDING)

        bids.forEach { bid ->
            if (OrderDirection.BUY != bid.direction) throw RuntimeException("Order direction for matching is wrong")
            val asks = orderRepository.findAllBy(
                OrderDirection.SELL,
                OrderStatus.PENDING,
                bid.price,
                bid.baseAccount.currency!!.code,
                bid.quoteAccount.currency!!.code,
                bid.baseAccount.id!!,
                bid.quoteAccount.id!!
            )
            for (ask in asks) {
                limitOrderEntityMatcher.match(bid, ask)
                if (bid.status == OrderStatus.FULFILLED) {
                    break
                }
            }
        }

    }


}
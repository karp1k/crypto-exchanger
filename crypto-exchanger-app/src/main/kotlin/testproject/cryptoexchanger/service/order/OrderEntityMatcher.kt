package testproject.cryptoexchanger.service.order

import testproject.cryptoexchanger.model.OrderEntity

interface OrderEntityMatcher {

    fun match(bid: OrderEntity, ask: OrderEntity)
}
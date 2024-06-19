package testproject.cryptoexchanger.dto

import testproject.cryptoexchanger.enumeration.OrderDirection
import java.math.BigDecimal

data class OrderBookDto(
    val baseCurrencyCode: String,
    val quoteCurrencyCode: String,
    val price: BigDecimal,
    val quantity: BigDecimal,
    val direction: OrderDirection,
)
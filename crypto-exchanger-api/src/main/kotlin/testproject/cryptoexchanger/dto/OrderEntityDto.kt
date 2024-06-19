package testproject.cryptoexchanger.dto

import testproject.cryptoexchanger.enumeration.OrderDirection
import testproject.cryptoexchanger.enumeration.OrderStatus
import java.math.BigDecimal
import java.time.Instant

data class OrderEntityDto (
    val id: Long,
    val price: BigDecimal,
    val baseCurrencyCode: String,
    val quoteCurrencyCode: String,
    val hold: BigDecimal,
    val quantity: BigDecimal,
    val quantityFulfilled: BigDecimal,
    val direction: OrderDirection,
    val status: OrderStatus,
    val createdAt: Instant,
    val modifiedAt: Instant
)
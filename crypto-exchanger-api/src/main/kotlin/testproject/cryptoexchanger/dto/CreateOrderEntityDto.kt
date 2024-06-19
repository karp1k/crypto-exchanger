package testproject.cryptoexchanger.dto

import testproject.cryptoexchanger.enumeration.OrderDirection
import java.math.BigDecimal

data class CreateOrderEntityDto(
    val price: BigDecimal,
    val quantity: BigDecimal,
    val baseCurrencyCode: String,
    val quoteCurrencyCode: String,
    val direction: OrderDirection,
    val walletAddressId: Long,
)

package testproject.cryptoexchanger.dto

import java.math.BigDecimal
import java.time.Instant

data class CurrencyRateDto(
    val baseCurrencyCode: String,
    val quoteCurrencyCode: String,
    val amount: Int,
    val rate: BigDecimal,
    val reportDateTime: Instant,
)

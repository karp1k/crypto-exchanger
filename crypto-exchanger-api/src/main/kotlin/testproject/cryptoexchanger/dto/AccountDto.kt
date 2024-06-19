package testproject.cryptoexchanger.dto

import java.math.BigDecimal

data class AccountDto(
    val currencyCode: String,
    val amount: BigDecimal,
)

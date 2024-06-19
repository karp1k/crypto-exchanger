package testproject.cryptoexchanger.dto

import java.math.BigDecimal

data class DepositPaymentRequestDto(
    val amount: BigDecimal,
    val cardNumber: Long,
    val cardHolder: String,
    val cvcCode: Int,
)

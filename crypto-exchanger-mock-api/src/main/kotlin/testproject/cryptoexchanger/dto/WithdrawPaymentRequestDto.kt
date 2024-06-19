package testproject.cryptoexchanger.dto

import java.math.BigDecimal

data class WithdrawPaymentRequestDto(
    val amount: BigDecimal,
    val cardNumber: Int,
)

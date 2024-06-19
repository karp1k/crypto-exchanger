package testproject.cryptoexchanger.dto

import testproject.cryptoexchanger.enumeration.PaymentResponseStatus
import java.time.Instant

data class PaymentResponseDto(
    val id: String,
    val status: PaymentResponseStatus,
    val errorMessage: String? = null,
    val createdAt: Instant = Instant.now(),
)

package testproject.cryptoexchanger.dto

import java.math.BigDecimal

data class WithdrawDto(
    val walletAddressId: Long,
    val amount: BigDecimal,
    val currencyCode: String,
    val cardDetailsDto: WithdrawCardDetailsDto,
) {
    data class WithdrawCardDetailsDto(
        val cardNumber: Int,
    )
}

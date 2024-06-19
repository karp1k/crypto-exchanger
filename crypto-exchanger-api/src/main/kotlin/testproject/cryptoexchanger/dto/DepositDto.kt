package testproject.cryptoexchanger.dto

import java.math.BigDecimal

data class DepositDto(
    val walletAddressId: Long,
    val amount: BigDecimal,
    val currencyCode: String,
    val cardDetailsDto: DepositCardDetailsDao,
) {
    data class DepositCardDetailsDao(
        val cardNumber: Long,
        val cardHolder: String,
        val cvcCode: Int,
    )
}

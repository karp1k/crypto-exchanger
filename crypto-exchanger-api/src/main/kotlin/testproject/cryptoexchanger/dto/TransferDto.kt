package testproject.cryptoexchanger.dto

import java.math.BigDecimal

data class TransferDto(
    val fromWalletAddressId: Long,
    val toWalletAddressId: Long,
    val amount: BigDecimal,
    val currencyCode: String,
)

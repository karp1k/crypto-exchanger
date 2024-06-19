package testproject.cryptoexchanger.dto

import testproject.cryptoexchanger.enumeration.TransactionStatus
import testproject.cryptoexchanger.enumeration.TransactionType
import java.math.BigDecimal

data class TransactionDto(
    val type: TransactionType,
    val status: TransactionStatus,
    val amount: BigDecimal?,
    val bidAmount: BigDecimal?,
    val askAmount: BigDecimal?,
    val fromWalletId: Long?,
    val toWalletId: Long?,
    val currencyCode: String?,
)

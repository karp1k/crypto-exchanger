package testproject.cryptoexchanger.mapper

import testproject.cryptoexchanger.dto.TransactionDto
import testproject.cryptoexchanger.model.Transaction

object TransactionMapper {

    fun toDto(transaction: Transaction): TransactionDto {
        return TransactionDto(
            type = transaction.type,
            status = transaction.status,
            amount = transaction.amount,
            bidAmount = transaction.bidAmount,
            askAmount = transaction.askAmount,
            fromWalletId = transaction.fromAccount?.walletAddress?.id,
            toWalletId = transaction.toAccount?.walletAddress?.id,
            currencyCode = transaction.currency?.code
        )
    }
}
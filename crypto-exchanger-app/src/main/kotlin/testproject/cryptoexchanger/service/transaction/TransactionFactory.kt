package testproject.cryptoexchanger.service.transaction

import testproject.cryptoexchanger.enumeration.TransactionStatus
import testproject.cryptoexchanger.enumeration.TransactionType
import testproject.cryptoexchanger.model.Account
import testproject.cryptoexchanger.model.Currency
import testproject.cryptoexchanger.model.OrderEntity
import testproject.cryptoexchanger.model.Transaction
import java.math.BigDecimal

object TransactionFactory {

    fun createOrderTradeTransaction(
        bid: OrderEntity,
        ask: OrderEntity,
        bidAmount: BigDecimal,
        askAmount: BigDecimal,
        status: TransactionStatus = TransactionStatus.FINISHED,
        type: TransactionType = TransactionType.ORDER_TRADE,
    ): Transaction = Transaction(
        bid = bid,
        ask = ask,
        bidAmount = bidAmount,
        askAmount = askAmount,
        status = status,
        type = type
    )

    fun createDepositTransaction(
        toAccount: Account,
        amount: BigDecimal,
        currency: Currency,
        status: TransactionStatus = TransactionStatus.PENDING,
        type: TransactionType = TransactionType.DEPOSIT,
        externalProviderTransactionId: String? = null,
        orderEntity: OrderEntity? = null,
    ): Transaction = Transaction(
        toAccount = toAccount,
        amount = amount,
        currency = currency,
        status = status,
        type = type,
        externalProviderTransactionId = externalProviderTransactionId,
    )

    fun createWithdrawTransaction(
        fromAccount: Account,
        amount: BigDecimal,
        currency: Currency,
        status: TransactionStatus = TransactionStatus.PENDING,
        type: TransactionType = TransactionType.WITHDRAW,
        externalProviderTransactionId: String? = null,
    ): Transaction = Transaction(
        fromAccount = fromAccount,
        amount = amount,
        currency = currency,
        status = status,
        type = type,
        externalProviderTransactionId = externalProviderTransactionId
    )

    fun createTransferTransaction(
        fromAccount: Account,
        toAccount: Account,
        amount: BigDecimal,
        currency: Currency,
        status: TransactionStatus = TransactionStatus.FINISHED,
        type: TransactionType = TransactionType.TRANSFER,
    ): Transaction = Transaction(
        fromAccount = fromAccount,
        toAccount = toAccount,
        amount = amount,
        currency = currency,
        status = status,
        type = type,
    )

}
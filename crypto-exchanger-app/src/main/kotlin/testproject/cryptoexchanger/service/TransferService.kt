package testproject.cryptoexchanger.service

import java.math.BigDecimal

interface TransferService {

    fun deposit(accountId: Long, amount: BigDecimal)
    fun withdraw(accountId: Long, amount: BigDecimal)
    fun transfer(fromAccountId: Long, toAccountId: Long, amount: BigDecimal)
}
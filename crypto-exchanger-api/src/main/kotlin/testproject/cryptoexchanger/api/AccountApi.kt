package testproject.cryptoexchanger.api

import testproject.cryptoexchanger.dto.DepositDto
import testproject.cryptoexchanger.dto.TransferDto
import testproject.cryptoexchanger.dto.WithdrawDto

interface AccountApi {

    fun deposit(dto: DepositDto)
    fun withdraw(dto: WithdrawDto)
    fun transfer(dto: TransferDto)
}
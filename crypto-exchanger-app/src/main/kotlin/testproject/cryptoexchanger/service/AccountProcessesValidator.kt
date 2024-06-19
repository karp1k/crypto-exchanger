package testproject.cryptoexchanger.service

import testproject.cryptoexchanger.dto.DepositDto
import testproject.cryptoexchanger.dto.TransferDto
import testproject.cryptoexchanger.dto.WithdrawDto
import testproject.cryptoexchanger.model.Account
import java.math.BigDecimal

class AccountProcessesValidator {

    fun validateDeposit(depositDto: DepositDto) {
        checkAmount(depositDto.amount)
    }

    fun validateWithdraw(withdrawDto: WithdrawDto, account: Account) {
        checkAmount(withdrawDto.amount)
        checkSufficientAmountForWithdraw(withdrawDto.amount, account)
    }

    fun validateTransferDto(transferDto: TransferDto, fromAccount: Account) {
        if (transferDto.fromWalletAddressId == transferDto.toWalletAddressId) {
            throw RuntimeException("Unable to Transfer on the same wallet address")
        }

        if (transferDto.amount.signum() == -1) {
            throw RuntimeException("The amount of transfer should be > 0")
        }

        checkAmount(transferDto.amount)
        checkSufficientAmountForWithdraw(transferDto.amount, fromAccount)
    }

    private fun checkSufficientAmountForWithdraw(amount: BigDecimal, account: Account) {
        val result = account.amount.add(amount.negate())
        if (result.signum() == -1) {
            throw RuntimeException("Insufficient amount on account ${account.id}")
        }
    }

    private fun checkAmount(amount: BigDecimal) {
        if (amount.signum() == -1) {
            throw RuntimeException("The amount of operation should be > 0")
        }
    }
}
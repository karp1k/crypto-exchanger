package testproject.cryptoexchanger.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import testproject.cryptoexchanger.model.Account
import testproject.cryptoexchanger.repository.AccountRepository
import java.math.BigDecimal

@Service
class SimpleTransferService(private val accountRepository: AccountRepository) : TransferService {

    @Transactional
    override fun deposit(accountId: Long, amount: BigDecimal) {
        accountRepository.findById(accountId)
            .ifPresent { accountRepository.save(changeAmount(it, amount)) }
    }

    @Transactional
    override fun withdraw(accountId: Long, amount: BigDecimal) {
        accountRepository.findById(accountId)
            .ifPresent { accountRepository.save(changeAmount(it, amount.negate())) }
    }

    @Transactional
    override fun transfer(fromAccountId: Long, toAccountId: Long, amount: BigDecimal) {
        if (fromAccountId == toAccountId) throw RuntimeException("Unable to transfer to the same account")
        val fromAccount: Account
        val toAccount: Account

        if (fromAccountId > toAccountId) {
            fromAccount = accountRepository.findById(fromAccountId).get()
            toAccount = accountRepository.findById(toAccountId).get()
        } else {
            toAccount = accountRepository.findById(toAccountId).get()
            fromAccount = accountRepository.findById(fromAccountId).get()
        }

        accountRepository.save(changeAmount(fromAccount, amount.negate()))
        accountRepository.save(changeAmount(toAccount, amount))
    }

    private fun changeAmount(account: Account, amount: BigDecimal): Account {
        val result: BigDecimal = account.amount.add(amount)
        if (result.signum() == -1) {
            throw RuntimeException("Insufficient amount on account ${account.id}")
        }
        account.amount = result
        return account
    }

}
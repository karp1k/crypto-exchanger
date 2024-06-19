package testproject.cryptoexchanger.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import testproject.cryptoexchanger.dto.DepositDto
import testproject.cryptoexchanger.dto.PaymentResponseDto
import testproject.cryptoexchanger.dto.TransferDto
import testproject.cryptoexchanger.dto.WithdrawDto
import testproject.cryptoexchanger.enumeration.PaymentResponseStatus
import testproject.cryptoexchanger.enumeration.TransactionStatus
import testproject.cryptoexchanger.model.Transaction
import testproject.cryptoexchanger.repository.AccountRepository
import testproject.cryptoexchanger.service.payment.PaymentGateway
import testproject.cryptoexchanger.service.transaction.TransactionFactory
import testproject.cryptoexchanger.service.transaction.TransactionService

@Service
class AccountProcessor(
    private val paymentGateway: PaymentGateway,
    private val transferService: TransferService,
    private val accountRepository: AccountRepository,
    private val transactionService: TransactionService,
) {

    private val accountProcessesValidator = AccountProcessesValidator()

    @Transactional
    fun deposit(depositDto: DepositDto, username: String) {
        accountProcessesValidator.validateDeposit(depositDto)

        val account = accountRepository.findByWalletAddressIdAndCurrencyCodeAndUsername(depositDto.walletAddressId,
            depositDto.currencyCode,
            username)

        val transaction =
            transactionService.createOrUpdate(TransactionFactory.createDepositTransaction(account,
                depositDto.amount,
                account.currency))
        val paymentResponse = paymentGateway.deposit(depositDto)
        handlePaymentResponse(paymentResponse, transaction) { transferService.deposit(account.id!!, depositDto.amount) }
    }

    @Transactional
    fun withdraw(withdrawDto: WithdrawDto, username: String) {
        val account = accountRepository.findByWalletAddressIdAndCurrencyCodeAndUsername(withdrawDto.walletAddressId,
            withdrawDto.currencyCode,
            username)
        accountProcessesValidator.validateWithdraw(withdrawDto, account)

        val transaction =
            transactionService.createOrUpdate(TransactionFactory.createWithdrawTransaction(account,
                withdrawDto.amount,
                account.currency))
        val paymentResponse = paymentGateway.withdraw(withdrawDto)
        handlePaymentResponse(paymentResponse, transaction) {
            transferService.withdraw(account.id!!,
                withdrawDto.amount)
        }
    }

    private fun handlePaymentResponse(paymentResponse: PaymentResponseDto, transaction: Transaction, run: Runnable) {
        if (PaymentResponseStatus.SUCCESS == paymentResponse.status) {
            run.run()

            transaction.apply {
                externalProviderTransactionId = paymentResponse.id
                status = TransactionStatus.FINISHED
            }.also { transactionService.createOrUpdate(it) }
        } else {
            transaction
                .apply { status = TransactionStatus.DENIED }
                .also { transactionService.createOrUpdate(it) }
        }
    }

    @Transactional
    fun transfer(transferDto: TransferDto, username: String) {
        val fromAccount =
            accountRepository.findByWalletAddressIdAndCurrencyCodeAndUsername(transferDto.fromWalletAddressId,
                transferDto.currencyCode,
                username)

        accountProcessesValidator.validateTransferDto(transferDto, fromAccount)

        val toAccount =
            accountRepository.findByWalletAddressNameAndCurrencyCode(transferDto.toWalletAddressId,
                transferDto.currencyCode)

        transferService.transfer(fromAccount.id!!, toAccount.id!!, transferDto.amount)
        transactionService.createOrUpdate(TransactionFactory.createTransferTransaction(fromAccount,
            toAccount,
            transferDto.amount,
            fromAccount.currency))
    }
}
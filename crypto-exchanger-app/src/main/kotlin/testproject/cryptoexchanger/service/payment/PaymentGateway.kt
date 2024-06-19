package testproject.cryptoexchanger.service.payment

import testproject.cryptoexchanger.dto.DepositDto
import testproject.cryptoexchanger.dto.PaymentResponseDto
import testproject.cryptoexchanger.dto.WithdrawDto

interface PaymentGateway {

    fun deposit(depositDto: DepositDto): PaymentResponseDto
    fun withdraw(withdrawDto: WithdrawDto): PaymentResponseDto
}
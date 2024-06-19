package testproject.cryptoexchanger.service.payment

import org.springframework.stereotype.Component
import testproject.cryptoexchanger.client.PaymentProviderClient
import testproject.cryptoexchanger.dto.DepositDto
import testproject.cryptoexchanger.dto.DepositPaymentRequestDto
import testproject.cryptoexchanger.dto.PaymentResponseDto
import testproject.cryptoexchanger.dto.WithdrawDto
import testproject.cryptoexchanger.dto.WithdrawPaymentRequestDto


@Component
class MockPaymentGateway(private val paymentProviderClient: PaymentProviderClient) : PaymentGateway {

    override fun deposit(depositDto: DepositDto): PaymentResponseDto {
        return paymentProviderClient.deposit(DepositPaymentRequestDto(
            amount = depositDto.amount,
            cardNumber = depositDto.cardDetailsDto.cardNumber,
            cardHolder = depositDto.cardDetailsDto.cardHolder,
            cvcCode = depositDto.cardDetailsDto.cvcCode
        ))
    }

    override fun withdraw(withdrawDto: WithdrawDto): PaymentResponseDto {
        return paymentProviderClient.withdraw(WithdrawPaymentRequestDto(
            amount = withdrawDto.amount,
            cardNumber = withdrawDto.cardDetailsDto.cardNumber
        ))

    }


}
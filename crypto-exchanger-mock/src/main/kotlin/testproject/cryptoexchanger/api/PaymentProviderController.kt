package testproject.cryptoexchanger.api


import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import testproject.cryptoexchanger.dto.DepositPaymentRequestDto
import testproject.cryptoexchanger.dto.PaymentResponseDto
import testproject.cryptoexchanger.dto.WithdrawPaymentRequestDto
import testproject.cryptoexchanger.enumeration.PaymentResponseStatus
import java.util.UUID

@RequestMapping(PaymentProviderApi.PATH_V1)
@RestController
class PaymentProviderController : PaymentProviderApi {

    override fun deposit(dto: DepositPaymentRequestDto): PaymentResponseDto {
        Thread.sleep(2000L) // pseudo payment process
        return PaymentResponseDto(UUID.randomUUID().toString(), PaymentResponseStatus.SUCCESS)
    }

    override fun withdraw(dto: WithdrawPaymentRequestDto): PaymentResponseDto {
        Thread.sleep(2000L) // pseudo payment process
        return PaymentResponseDto(UUID.randomUUID().toString(), PaymentResponseStatus.SUCCESS)
    }
}
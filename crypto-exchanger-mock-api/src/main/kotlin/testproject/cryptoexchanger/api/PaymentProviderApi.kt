package testproject.cryptoexchanger.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import testproject.cryptoexchanger.dto.DepositPaymentRequestDto
import testproject.cryptoexchanger.dto.PaymentResponseDto
import testproject.cryptoexchanger.dto.WithdrawPaymentRequestDto

interface PaymentProviderApi {

    @PostMapping("/deposit")
    fun deposit(@RequestBody dto: DepositPaymentRequestDto): PaymentResponseDto

    @PostMapping("/withdraw")
    fun withdraw(@RequestBody dto: WithdrawPaymentRequestDto): PaymentResponseDto

    companion object {
        const val PATH_V1 = "/api/v1/"
    }
}
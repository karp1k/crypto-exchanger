package testproject.cryptoexchanger.client

import org.springframework.cloud.openfeign.FeignClient
import testproject.cryptoexchanger.api.PaymentProviderApi

@FeignClient(
    name = "mock-payment-provider", url = "\${app.services.crypto-exchanger-mock.url}",
    path = PaymentProviderApi.PATH_V1
)
interface PaymentProviderClient : PaymentProviderApi {
}
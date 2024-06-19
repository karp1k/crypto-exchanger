package testproject.cryptoexchanger.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import testproject.cryptoexchanger.service.currencyrate.CurrencyRateWsHandler


@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val currencyRateWsHandler: CurrencyRateWsHandler,
) : WebSocketConfigurer {

    companion object {
        const val WS_CURRENCY_RATE_V1 = "/ws/v1/currency-rate"
    }

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(currencyRateWsHandler, WS_CURRENCY_RATE_V1)
            .setAllowedOrigins("*")
    }
}
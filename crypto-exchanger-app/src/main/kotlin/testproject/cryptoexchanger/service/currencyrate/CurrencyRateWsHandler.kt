package testproject.cryptoexchanger.service.currencyrate

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.util.UriComponentsBuilder
import testproject.cryptoexchanger.dto.CurrencyRateDto
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Component
class CurrencyRateWsHandler(
    private val objectMapper: ObjectMapper,
    private val currencyRateConsumer: CurrencyRateConsumer,
) : TextWebSocketHandler(), CurrencyRateSubscriber {

    private val subscribers: ConcurrentMap<String, MutableSet<WebSocketSession>> = ConcurrentHashMap()

    @PostConstruct
    fun init() {
        currencyRateConsumer.subscribe(this)
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val currencyPair = UriComponentsBuilder.fromUri(session.uri!!).build().queryParams["pair"]!![0]
        subscribers.computeIfAbsent(currencyPair) { mutableSetOf(session) }
        subscribers.computeIfPresent(currencyPair) { _, b -> b.add(session).run { b } }
        super.afterConnectionEstablished(session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val currencyPair = UriComponentsBuilder.fromUri(session.uri!!).build().queryParams["pair"]!![0]
        subscribers[currencyPair]?.remove(session)
        super.afterConnectionClosed(session, status)
    }

    override fun notify(dto: CurrencyRateDto) {
        val key = "${dto.baseCurrencyCode}/${dto.quoteCurrencyCode}"
        subscribers[key]?.forEach {
            if (it.isOpen) it.sendMessage(TextMessage(objectMapper.writeValueAsString(dto)))
        }
    }
}

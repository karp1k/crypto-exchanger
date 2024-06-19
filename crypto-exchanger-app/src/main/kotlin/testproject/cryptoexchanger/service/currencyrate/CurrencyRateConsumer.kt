package testproject.cryptoexchanger.service.currencyrate

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import testproject.cryptoexchanger.dto.CurrencyRateDto
import java.util.concurrent.CompletableFuture

@Component
class CurrencyRateConsumer(private val currencyRateService: CurrencyRateService) {

    private val subscribers: MutableSet<CurrencyRateSubscriber> = mutableSetOf()

    @KafkaListener(topics = ["\${app.currency-rate.topic}"])
    fun handle(record: ConsumerRecord<String, CurrencyRateDto>) {
        currencyRateService.create(record.value())
        notifySubscribers(record.value())
    }

    fun subscribe(subscriber: CurrencyRateSubscriber) {
        subscribers.add(subscriber)
    }

    fun notifySubscribers(dto: CurrencyRateDto) {
        subscribers.forEach { CompletableFuture.runAsync { it.notify(dto) } }
    }

}
package testproject.cryptoexchanger.currencyrateprovider

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import testproject.cryptoexchanger.dto.CurrencyRateDto
import java.util.Random
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant

@Component
class CurrencyRateProducer(private val kafkaTemplate: KafkaTemplate<String, CurrencyRateDto>) {

    @Value("\${app.currency-rate.topic}")
    val topicName: String? = null

    val random = Random()

    val currencyPairs: Set<Pair<CurrencyRateDto, Double>> = initializeCurrencyRatePairs()

    // every 10 seconds
    @Scheduled(cron = "\${app.currency-rate.producer.cron}")
    fun send() {
        val now = Instant.now()
        currencyPairs.forEach {

            val copy = it.first.copy(
                rate = BigDecimal(random.nextDouble(it.first.rate.toDouble(), it.second)).setScale(
                    2,
                    RoundingMode.HALF_EVEN
                ),
                reportDateTime = now
            )

            kafkaTemplate.send(topicName!!, copy)
        }
    }

    private fun initializeCurrencyRatePairs(): Set<Pair<CurrencyRateDto, Double>> {
        val now = Instant.now()
        val amount = 1
        return setOf(
            Pair(
                CurrencyRateDto(
                    baseCurrencyCode = "BTC",
                    quoteCurrencyCode = "ETH",
                    rate = BigDecimal("18.61"),
                    amount = amount,
                    reportDateTime = now
                ), 20.00
            ),
            Pair(
                CurrencyRateDto(
                    baseCurrencyCode = "BTC",
                    quoteCurrencyCode = "USDT",
                    rate = BigDecimal("66167.35"),
                    amount = amount,
                    reportDateTime = now
                ), 66300.00
            ),
            Pair(
                CurrencyRateDto(
                    baseCurrencyCode = "BTC",
                    quoteCurrencyCode = "EUR",
                    rate = BigDecimal("61911.73"),
                    amount = amount,
                    reportDateTime = now
                ), 63000.00
            ),
            Pair(
                CurrencyRateDto(
                    baseCurrencyCode = "ETH",
                    quoteCurrencyCode = "USDT",
                    rate = BigDecimal("3554.48"),
                    amount = amount,
                    reportDateTime = now
                ), 3600.00
            ),
            Pair(
                CurrencyRateDto(
                    baseCurrencyCode = "ETH",
                    quoteCurrencyCode = "EUR",
                    rate = BigDecimal("3324.81"),
                    amount = amount,
                    reportDateTime = now
                ), 3400.00
            ),
            Pair(
                CurrencyRateDto(
                    baseCurrencyCode = "USDT",
                    quoteCurrencyCode = "USD",
                    rate = BigDecimal.ONE,
                    amount = amount,
                    reportDateTime = now
                ), 1.01
            ),
            Pair(
                CurrencyRateDto(
                    baseCurrencyCode = "USD",
                    quoteCurrencyCode = "EUR",
                    rate = BigDecimal("0.93"),
                    amount = amount,
                    reportDateTime = now
                ), 1.00
            ),
        )
    }


}
package testproject.cryptoexchanger.kaffka.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.test.EmbeddedKafkaBroker

@Configuration
class KafkaConfig {

    @Bean
    fun broker(): EmbeddedKafkaBroker {
        val properties: MutableMap<String, String> = HashMap()
        properties["listeners"] = "PLAINTEXT://localhost:9092"
        properties["advertised.listeners"] = "PLAINTEXT://localhost:9092"
        properties["listener.security.protocol.map"] = "PLAINTEXT:PLAINTEXT"
        val broker = EmbeddedKafkaBroker(1, false, 1, "currency-rate")
            .brokerProperties(properties)
            .kafkaPorts(9092)
            .brokerListProperty("spring.kafka.bootstrap-servers")
        return broker
    }
}
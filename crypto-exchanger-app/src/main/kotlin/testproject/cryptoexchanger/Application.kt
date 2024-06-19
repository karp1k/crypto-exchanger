package testproject.cryptoexchanger

import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
//import org.springframework.cloud.commons.httpclient.HttpClientConfiguration
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

// fix https://stackoverflow.com/questions/74593433/consider-defining-a-bean-of-type-org-springframework-cloud-openfeign-feignconte
@ImportAutoConfiguration(classes = [FeignAutoConfiguration::class/*, HttpClientConfiguration::class*/])
@EnableFeignClients
@EnableScheduling
@EnableJpaAuditing
@EnableCaching
@SpringBootApplication
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}

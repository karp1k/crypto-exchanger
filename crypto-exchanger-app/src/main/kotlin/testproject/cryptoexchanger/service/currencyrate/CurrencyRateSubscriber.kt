package testproject.cryptoexchanger.service.currencyrate

import testproject.cryptoexchanger.dto.CurrencyRateDto

/**
 * Subscriber to [CurrencyRateConsumer]
 */
interface CurrencyRateSubscriber {

    fun notify(dto: CurrencyRateDto)
}
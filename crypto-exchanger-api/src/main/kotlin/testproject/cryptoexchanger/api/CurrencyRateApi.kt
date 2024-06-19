package testproject.cryptoexchanger.api

import testproject.cryptoexchanger.dto.CurrencyRatePairDto

interface CurrencyRateApi {

    fun getAvailablePairs(): Set<CurrencyRatePairDto>
}
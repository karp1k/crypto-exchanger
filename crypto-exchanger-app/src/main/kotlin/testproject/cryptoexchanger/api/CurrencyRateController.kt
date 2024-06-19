package testproject.cryptoexchanger.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import testproject.cryptoexchanger.api.CurrencyRateController.Companion.PATH_V1
import testproject.cryptoexchanger.dto.CurrencyRatePairDto
import testproject.cryptoexchanger.service.currencyrate.CurrencyRateService

@RequestMapping(PATH_V1)
@RestController
class CurrencyRateController(private val currencyRateService: CurrencyRateService) : CurrencyRateApi {

    companion object {
        const val PATH_V1 = "/api/v1/currency-rate"
    }

    @GetMapping("/pairs")
    override fun getAvailablePairs(): Set<CurrencyRatePairDto> {
        return currencyRateService.getCurrencyPairs()
    }

}
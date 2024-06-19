package testproject.cryptoexchanger.mapper

import testproject.cryptoexchanger.dto.CurrencyRateDto
import testproject.cryptoexchanger.dto.CurrencyRatePairDto
import testproject.cryptoexchanger.model.Currency
import testproject.cryptoexchanger.model.CurrencyPairProjection
import testproject.cryptoexchanger.model.CurrencyRate

object CurrencyRateMapper {

    fun toEntity(
        dto: CurrencyRateDto,
        baseCurrencyProxy: Currency,
        quoteCurrencyProxy: Currency,
    ): CurrencyRate {
        return CurrencyRate(
            baseCurrency = baseCurrencyProxy,
            quoteCurrency = quoteCurrencyProxy,
            rate = dto.rate,
            amount = dto.amount,
            reportDateTime = dto.reportDateTime,
        )
    }

    fun toCurrencyRatePairDto(entity: CurrencyPairProjection): CurrencyRatePairDto {
        return CurrencyRatePairDto(entity.baseCurrencyCode, entity.quoteCurrencyCode)
    }

}
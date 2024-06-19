package testproject.cryptoexchanger.repository;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import testproject.cryptoexchanger.model.CurrencyPairProjection
import testproject.cryptoexchanger.model.CurrencyRate

interface CurrencyRateRepository : JpaRepository<CurrencyRate, Long> {

    @Query("select distinct base_currency_code as baseCurrencyCode, quote_currency_code as quoteCurrencyCode from currency_rate",
        nativeQuery = true)
    fun findDistinctCurrencyPairs(): List<CurrencyPairProjection>

}
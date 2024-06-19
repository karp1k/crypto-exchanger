package testproject.cryptoexchanger.model

interface CurrencyPairProjection {
    val baseCurrencyCode: String
    val quoteCurrencyCode: String
}
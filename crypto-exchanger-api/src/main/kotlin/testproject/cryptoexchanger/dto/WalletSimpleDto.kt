package testproject.cryptoexchanger.dto

data class WalletSimpleDto(val name: String, val addresses: Set<String>? = null)
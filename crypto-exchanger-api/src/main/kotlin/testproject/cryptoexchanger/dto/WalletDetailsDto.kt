package testproject.cryptoexchanger.dto

data class WalletDetailsDto(
    val name: String,
    val addresses: Set<WalletAddressDto>,
)

package testproject.cryptoexchanger.dto

data class WalletAddressDto(
    val name: String,
    val id: Long,
    val accounts: Set<AccountDto>,
)

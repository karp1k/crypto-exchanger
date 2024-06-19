package testproject.cryptoexchanger.model


interface WalletNameAndAddressesProjection {
    val name: String
    val addresses: List<String>
}
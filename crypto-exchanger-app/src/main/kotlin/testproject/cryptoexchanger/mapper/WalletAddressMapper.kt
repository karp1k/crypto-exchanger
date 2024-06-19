package testproject.cryptoexchanger.mapper

import testproject.cryptoexchanger.dto.WalletAddressDto
import testproject.cryptoexchanger.model.WalletAddress

object WalletAddressMapper {

    fun toDto(walletAddress: WalletAddress): WalletAddressDto {
        return WalletAddressDto(
            id = walletAddress.id!!,
            name = walletAddress.name,
            accounts = walletAddress.accounts.mapTo(HashSet()) { AccountMapper.toDto(it) }
        )
    }
}
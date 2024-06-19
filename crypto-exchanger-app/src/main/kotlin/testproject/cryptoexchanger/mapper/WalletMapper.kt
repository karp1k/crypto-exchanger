package testproject.cryptoexchanger.mapper

import testproject.cryptoexchanger.dto.WalletDetailsDto
import testproject.cryptoexchanger.dto.WalletSimpleDto
import testproject.cryptoexchanger.model.Wallet
import testproject.cryptoexchanger.model.WalletNameAndAddressesProjection

object WalletMapper {

    fun toDto(wallet: Wallet): WalletDetailsDto {
        return WalletDetailsDto(
            name = wallet.name,
            addresses = wallet.walletAddresses.mapTo(HashSet()) { WalletAddressMapper.toDto(it) }
        )
    }

    fun toSimpleDto(walletNameAndAddressesProjection: WalletNameAndAddressesProjection): WalletSimpleDto {
        return WalletSimpleDto(
            name = walletNameAndAddressesProjection.name,
            addresses = walletNameAndAddressesProjection.addresses.mapTo(HashSet()) { it })
    }

}
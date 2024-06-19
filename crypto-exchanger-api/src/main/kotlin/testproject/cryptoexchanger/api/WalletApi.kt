package testproject.cryptoexchanger.api

import testproject.cryptoexchanger.dto.WalletDetailsDto
import testproject.cryptoexchanger.dto.WalletSimpleDto

interface WalletApi {

    fun read(walletName: String): WalletDetailsDto
    fun list(): Set<WalletSimpleDto>
}
package testproject.cryptoexchanger.service

import org.springframework.stereotype.Service
import testproject.cryptoexchanger.dto.WalletDetailsDto
import testproject.cryptoexchanger.dto.WalletSimpleDto
import testproject.cryptoexchanger.mapper.WalletMapper
import testproject.cryptoexchanger.repository.WalletRepository

@Service
class WalletService(private val walletRepository: WalletRepository) {

    fun read(walletName: String, username: String): WalletDetailsDto {
        return walletRepository.findByNameAndUserEntityUsername(walletName, username)?.let {
            WalletMapper.toDto(it)
        } ?: throw RuntimeException("wallet with name: $walletName not found")
    }

    fun list(username: String): Set<WalletSimpleDto> {
        return walletRepository.findNameAndAddressesBy(username).mapTo(HashSet()) { WalletMapper.toSimpleDto(it) }
    }

}

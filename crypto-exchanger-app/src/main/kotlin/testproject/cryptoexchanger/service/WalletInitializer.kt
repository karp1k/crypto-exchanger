package testproject.cryptoexchanger.service

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import testproject.cryptoexchanger.model.Account
import testproject.cryptoexchanger.model.UserEntity
import testproject.cryptoexchanger.model.Wallet
import testproject.cryptoexchanger.model.WalletAddress
import testproject.cryptoexchanger.repository.CurrencyRepository
import testproject.cryptoexchanger.repository.WalletRepository

@Component
class WalletInitializer(
    private val walletRepository: WalletRepository,
    private val currencyRepository: CurrencyRepository,
) {

    companion object {
        const val DEFAULT_ADDRESS_NAME = "Address 1";
        const val DEFAULT_WALLET_NAME = "Wallet 1";
    }

    @Transactional
    fun initializeNewWallet(user: UserEntity): Wallet {
        val wallet = Wallet(userEntity = user, name = DEFAULT_WALLET_NAME)
        user.wallets.add(wallet)
        return initialize(wallet)
    }

    private fun initialize(wallet: Wallet, walletAddressName: String = DEFAULT_ADDRESS_NAME): Wallet {
        val address = WalletAddress(name = walletAddressName, wallet = wallet)
        val accounts = currencyRepository.findAll().mapTo(HashSet()) { currency ->
            Account(currency = currency, walletAddress = address)
        }
        address.accounts = accounts
        wallet.walletAddresses.add(address)
        return walletRepository.save(wallet)
    }


}
package testproject.cryptoexchanger.bootsrap

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import testproject.cryptoexchanger.dto.UserEntityDto
import testproject.cryptoexchanger.enumeration.CurrencyType
import testproject.cryptoexchanger.model.Currency
import testproject.cryptoexchanger.repository.CurrencyRepository
import testproject.cryptoexchanger.service.user.UserEntityService

@Component
class Bootstrapper(
    private val repository: CurrencyRepository,
    private val userEntityService: UserEntityService,
    private val encoder: PasswordEncoder,
) {

    @PostConstruct
    fun init() {
        repository.saveAllAndFlush(
            mutableListOf(
                Currency("BTC", "Bitcoin", CurrencyType.CRYPTO),
                Currency("ETH", "Etherium", CurrencyType.CRYPTO),
                Currency("USDT", "Tether (USDT)", CurrencyType.CRYPTO),
                Currency("USD", "US Dollar", CurrencyType.FIAT),
                Currency("EUR", "Euro", CurrencyType.FIAT)
            )
        )

        userEntityService.create(UserEntityDto("johndoe", "johndoe@sample.com", encoder.encode("q123")))

    }
}
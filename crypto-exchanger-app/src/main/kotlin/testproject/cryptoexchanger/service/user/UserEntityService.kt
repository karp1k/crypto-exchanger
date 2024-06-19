package testproject.cryptoexchanger.service.user

//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import testproject.cryptoexchanger.dto.UserEntityDto
import testproject.cryptoexchanger.mapper.UserEntityMapper
import testproject.cryptoexchanger.model.UserEntity
import testproject.cryptoexchanger.repository.UserEntityRepository
import testproject.cryptoexchanger.service.WalletInitializer

@Service
class UserEntityService(
    private val repository: UserEntityRepository,
    private val walletInitializer: WalletInitializer,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {

    @Transactional
    fun create(dto: UserEntityDto): UserEntity {
        val entity = repository.save(UserEntityMapper.toEntity(dto, passwordEncoder.encode(dto.password)))
        walletInitializer.initializeNewWallet(entity)
        return entity;
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        return repository.findByUsername(username!!) ?: throw RuntimeException("Invalid username")
    }


}
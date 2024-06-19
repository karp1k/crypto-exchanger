package testproject.cryptoexchanger.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    @get:JvmName("_username") @get:JvmSynthetic // workaround for https://youtrack.jetbrains.com/issue/KT-6653
    val username: String,

    @Column(nullable = false, unique = true)
    @get:JvmName("_email") @get:JvmSynthetic // workaround for https://youtrack.jetbrains.com/issue/KT-6653
    val email: String,

    @Column(nullable = false)
    @get:JvmName("_password") @get:JvmSynthetic // workaround for https://youtrack.jetbrains.com/issue/KT-6653
    val password: String,

    @OneToMany(mappedBy = "userEntity")
    var wallets: MutableSet<Wallet> = mutableSetOf(),
) : EntityWithCreateAtAndModifiedAt(), UserDetails {

    // todo remove stub
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableSetOf(SimpleGrantedAuthority("USER"))

    override fun getPassword(): String = password

    override fun getUsername(): String = username
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

}
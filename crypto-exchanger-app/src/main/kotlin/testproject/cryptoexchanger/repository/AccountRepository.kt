package testproject.cryptoexchanger.repository;

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import testproject.cryptoexchanger.model.Account
import java.util.Optional

interface AccountRepository : JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    override fun findById(id: Long): Optional<Account>

    //todo possibly required lock??
    @Query("select a from Account a " +
            "join fetch a.walletAddress wa " +
            "join fetch wa.wallet w " +
            "join fetch w.userEntity u " +
            "join fetch a.currency c " +
            "where u.username = (:username) " +
            "and wa.id = (:walletAddressId) " +
            "and c.code = (:currencyCode)")
    fun findByWalletAddressIdAndCurrencyCodeAndUsername(
        walletAddressId: Long,
        currencyCode: String,
        username: String,
    ): Account

    @Query("select a from Account a " +
            "join fetch a.walletAddress wa " +
            "join fetch wa.wallet w " +
            "join fetch w.userEntity u " +
            "join fetch a.currency c " +
            "where wa.id = (:walletAddressId) " +
            "and c.code = (:currencyCode)")
    fun findByWalletAddressNameAndCurrencyCode(walletAddressId: Long, currencyCode: String): Account

}
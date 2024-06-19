package testproject.cryptoexchanger.repository;

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import testproject.cryptoexchanger.model.Wallet
import testproject.cryptoexchanger.model.WalletNameAndAddressesProjection

interface WalletRepository : JpaRepository<Wallet, Long> {


    @EntityGraph(
        type = EntityGraph.EntityGraphType.FETCH,
        attributePaths = [
            "walletAddresses",
            "walletAddresses.accounts",
            "walletAddresses.accounts.currency",
            "userEntity" // had to fetch bc @ManyToOne(fetch = FetchType.LAZY, optional = false) not working
        ]
    )
    fun findByNameAndUserEntityUsername(name: String, username: String): Wallet?

    @Query(value = "select w.name as name, " +
            "(select name as walletAddressName from wallet_address wa where wa.wallet_id = w.id) as addresses from wallet w " +
            "left join user_entity u on u.id = w.user_entity_id " +
            " where u.username = (:username) ", nativeQuery = true)
    fun findNameAndAddressesBy(username: String): Set<WalletNameAndAddressesProjection>
}
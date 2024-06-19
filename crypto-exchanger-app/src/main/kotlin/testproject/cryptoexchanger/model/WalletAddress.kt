package testproject.cryptoexchanger.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class WalletAddress(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false, updatable = false)
    var wallet: Wallet,

    @OneToMany(mappedBy = "walletAddress", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var accounts: MutableSet<Account> = mutableSetOf(),

    ) : EntityWithCreateAtAndModifiedAt() {

}
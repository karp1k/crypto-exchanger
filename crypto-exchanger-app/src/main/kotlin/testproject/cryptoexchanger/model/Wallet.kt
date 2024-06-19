package testproject.cryptoexchanger.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Wallet(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,

    @Column
    val name: String,

    @OneToMany(mappedBy = "wallet", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var walletAddresses: MutableSet<WalletAddress> = mutableSetOf(),


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_entity_id", updatable = false, nullable = false)
    var userEntity: UserEntity,

    )
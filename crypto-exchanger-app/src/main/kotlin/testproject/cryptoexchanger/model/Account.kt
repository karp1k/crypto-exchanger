package testproject.cryptoexchanger.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.math.BigDecimal

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(name = "uk-wallet_address_id-and-currency_id",
            columnNames = ["wallet_address_id", "currency_code"])
    ]
)
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "wallet_address_id", nullable = false, updatable = false)
    var walletAddress: WalletAddress,

    @Column(nullable = false, scale = 2)
    var amount: BigDecimal = BigDecimal.ZERO,

    @ManyToOne(optional = false)
    @JoinColumn(name = "currency_code", nullable = false, updatable = false)
    var currency: Currency,

    )
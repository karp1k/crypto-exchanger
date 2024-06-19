package testproject.cryptoexchanger.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import testproject.cryptoexchanger.enumeration.TransactionStatus
import testproject.cryptoexchanger.enumeration.TransactionType
import java.math.BigDecimal

@Entity
class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq")
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column
    var externalProviderTransactionId: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    var type: TransactionType,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TransactionStatus,

    @Column(updatable = false, scale = 2)
    var amount: BigDecimal? = null,

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    var fromAccount: Account? = null,

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    var toAccount: Account? = null,

    @ManyToOne // workaround for transactions connected with only one order
    @JoinColumn(name = "order_entity_id")
    var orderEntity: OrderEntity? = null,

    @ManyToOne
    @JoinColumn(name = "bid_id")
    var bid: OrderEntity? = null,

    @Column(scale = 2)
    var bidAmount: BigDecimal? = null,

    @ManyToOne
    @JoinColumn(name = "ask_id")
    var ask: OrderEntity? = null,

    @Column(scale = 2)
    var askAmount: BigDecimal? = null,

    // denormalization of the db model
    @ManyToOne
    @JoinColumn(name = "currency_code", nullable = true, updatable = false)
    var currency: Currency? = null,
) : EntityWithCreateAtAndModifiedAt()
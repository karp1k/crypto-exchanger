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
import testproject.cryptoexchanger.enumeration.OrderDirection
import testproject.cryptoexchanger.enumeration.OrderStatus
import testproject.cryptoexchanger.enumeration.OrderType
import java.math.BigDecimal

@Entity
class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq")
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(nullable = false, updatable = false)
    var price: BigDecimal,

    @Column(nullable = false)
    var hold: BigDecimal,

    @Column(nullable = false, updatable = false)
    var quantity: BigDecimal,

    @Column(nullable = false)
    var quantityFulfilled: BigDecimal = BigDecimal.ZERO,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    var direction: OrderDirection,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    var type: OrderType = OrderType.LIMIT,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: OrderStatus = OrderStatus.PENDING,

    @ManyToOne
    @JoinColumn(name = "origin_account_id")
    var baseAccount: Account,

    @ManyToOne
    @JoinColumn(name = "target_account_id")
    var quoteAccount: Account,

    @ManyToOne
    @JoinColumn(name = "username")
    var userEntity: UserEntity,

    ) : EntityWithCreateAtAndModifiedAt() {

    fun getRemainingQuantity(): BigDecimal = quantity.subtract(quantityFulfilled)
}
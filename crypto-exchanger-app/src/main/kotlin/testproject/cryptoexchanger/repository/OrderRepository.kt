package testproject.cryptoexchanger.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import testproject.cryptoexchanger.enumeration.OrderDirection
import testproject.cryptoexchanger.enumeration.OrderStatus
import testproject.cryptoexchanger.model.OrderEntity
import java.math.BigDecimal

interface OrderRepository : JpaRepository<OrderEntity, Long> {

    @Transactional(readOnly = true)
    @Query("select oe from OrderEntity oe " +
            "join oe.baseAccount ba " +
            "join oe.quoteAccount qa " +
            "where oe.direction = (:direction) " +
            "and oe.status = (:status) " +
            "and oe.price <= (:price) " +
            "and ba.currency.code = (:baseCurrencyCode) " +
            "and qa.currency.code = (:quoteCurrencyCode) " +
            "and ba.id <> (:baseAccountId) " +
            "and qa.id <> (:quoteAccountId)")
    fun findAllBy(
        direction: OrderDirection,
        status: OrderStatus,
        price: BigDecimal,
        baseCurrencyCode: String,
        quoteCurrencyCode: String,
        baseAccountId: Long,
        quoteAccountId: Long,
    ): Set<OrderEntity>

    fun findAllByDirectionAndStatusOrderByCreatedAtDesc(
        direction: OrderDirection,
        status: OrderStatus,
    ): Set<OrderEntity>

    fun findAllByStatus(status: OrderStatus): Set<OrderEntity>

    @Query("select o from OrderEntity o " +
            "join fetch o.baseAccount a " +
            "join fetch a.walletAddress wa " +
            "join fetch wa.wallet w " +
            "join fetch w.userEntity u " +
            "where u.username = (:username)")
    fun findAllByUsername(username: String): Set<OrderEntity>
}
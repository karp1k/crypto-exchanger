package testproject.cryptoexchanger.model


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.Instant

@Entity
class CurrencyRate(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "base_currency_code", nullable = true, updatable = false)
    var baseCurrency: Currency? = null,

    @ManyToOne
    @JoinColumn(name = "quote_currency_code", nullable = true, updatable = false)
    var quoteCurrency: Currency? = null,

    @Column(nullable = false, updatable = false)
    var amount: Int,

    @Column(nullable = false, updatable = false, scale = 2)
    var rate: BigDecimal,

    @Column(nullable = false, updatable = false)
    var reportDateTime: Instant,

    )
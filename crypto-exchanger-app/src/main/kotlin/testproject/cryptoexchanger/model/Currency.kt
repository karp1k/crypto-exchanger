package testproject.cryptoexchanger.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import testproject.cryptoexchanger.enumeration.CurrencyType
import java.time.Instant

@Entity
class Currency(

    @Id
    var code: String,

    @Column(nullable = false)
    var name: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: CurrencyType? = null,

    @LastModifiedDate
    @Column(nullable = false)
    var modifiedAt: Instant? = null,

    ) : EntityWithCreateAt(), Persistable<String> {
    override fun getId(): String {
        return code
    }

    @Transient
    private var isNew: Boolean = true

    override fun isNew(): Boolean {
        return isNew
    }
}
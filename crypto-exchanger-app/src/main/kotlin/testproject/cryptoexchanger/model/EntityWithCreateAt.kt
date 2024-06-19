package testproject.cryptoexchanger.model

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class EntityWithCreateAt(
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: Instant? = null,
)
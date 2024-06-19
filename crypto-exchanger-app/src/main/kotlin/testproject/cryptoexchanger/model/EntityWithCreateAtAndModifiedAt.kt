package testproject.cryptoexchanger.model

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class EntityWithCreateAtAndModifiedAt(
    @LastModifiedDate
    @Column(nullable = false, updatable = false)
    var modifiedAt: Instant? = null,
) : EntityWithCreateAt()
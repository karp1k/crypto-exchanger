package testproject.cryptoexchanger.repository

import org.springframework.data.jpa.repository.JpaRepository
import testproject.cryptoexchanger.model.UserEntity

interface UserEntityRepository : JpaRepository<UserEntity, Long> {

    fun findByUsername(username: String): UserEntity?
}
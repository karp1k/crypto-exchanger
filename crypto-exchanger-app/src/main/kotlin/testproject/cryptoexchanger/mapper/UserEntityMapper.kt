package testproject.cryptoexchanger.mapper

import testproject.cryptoexchanger.dto.UserEntityDto
import testproject.cryptoexchanger.model.UserEntity

object UserEntityMapper {

    fun toEntity(dto: UserEntityDto, encodedPassword: String): UserEntity = UserEntity(
        username = dto.username,
        email = dto.email,
        password = encodedPassword)

}
package testproject.cryptoexchanger.api

import testproject.cryptoexchanger.dto.UserEntityDto

interface UserEntityApi {

    fun create(dto: UserEntityDto)

}
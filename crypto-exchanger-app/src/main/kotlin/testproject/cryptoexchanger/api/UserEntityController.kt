package testproject.cryptoexchanger.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import testproject.cryptoexchanger.api.UserEntityController.Companion.PATH_V1
import testproject.cryptoexchanger.dto.UserEntityDto
import testproject.cryptoexchanger.service.user.UserEntityService

@RequestMapping(PATH_V1)
@RestController
class UserEntityController(private val userEntityService: UserEntityService) : UserEntityApi {

    companion object {
        const val PATH_V1 = "/api/v1/user"
    }

    @PostMapping
    override fun create(@RequestBody dto: UserEntityDto) {
        userEntityService.create(dto)
    }

}
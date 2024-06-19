package testproject.cryptoexchanger.api

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import testproject.cryptoexchanger.api.AccountController.Companion.PATH_V1
import testproject.cryptoexchanger.dto.DepositDto
import testproject.cryptoexchanger.dto.TransferDto
import testproject.cryptoexchanger.dto.WithdrawDto
import testproject.cryptoexchanger.service.AccountProcessor

@RequestMapping(PATH_V1)
@RestController
class AccountController(private val accountProcessor: AccountProcessor) : AccountApi {

    companion object {
        const val PATH_V1 = "/api/v1/account"
    }

    @PostMapping("/deposit")
    override fun deposit(@RequestBody dto: DepositDto) {
        accountProcessor.deposit(dto, SecurityContextHolder.getContext().authentication.name)
    }

    @PostMapping("/withdraw")
    override fun withdraw(@RequestBody dto: WithdrawDto) {
        accountProcessor.withdraw(dto, SecurityContextHolder.getContext().authentication.name)
    }

    @PostMapping("/transfer")
    override fun transfer(@RequestBody dto: TransferDto) {
        accountProcessor.transfer(dto, SecurityContextHolder.getContext().authentication.name)
    }

}
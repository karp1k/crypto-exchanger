package testproject.cryptoexchanger.api

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import testproject.cryptoexchanger.api.WalletController.Companion.PATH_V1
import testproject.cryptoexchanger.dto.WalletDetailsDto
import testproject.cryptoexchanger.dto.WalletSimpleDto
import testproject.cryptoexchanger.service.WalletService

@RequestMapping(PATH_V1)
@RestController
class WalletController(private val walletService: WalletService) : WalletApi {

    companion object {
        const val PATH_V1 = "/api/v1/wallet"
    }

    @GetMapping("/{walletName}")
    override fun read(@PathVariable walletName: String): WalletDetailsDto {
        return walletService.read(walletName, SecurityContextHolder.getContext().authentication.name)
    }

    @GetMapping
    override fun list(): Set<WalletSimpleDto> {
        return walletService.list(SecurityContextHolder.getContext().authentication.name);
    }
}
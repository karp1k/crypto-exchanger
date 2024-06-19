package testproject.cryptoexchanger.api

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import testproject.cryptoexchanger.api.TransactionController.Companion.PATH_V1
import testproject.cryptoexchanger.dto.TransactionDto
import testproject.cryptoexchanger.service.transaction.TransactionService

@RequestMapping(PATH_V1)
@RestController
class TransactionController(private val transactionService: TransactionService): TransactionApi {

    companion object {
        const val PATH_V1 = "/api/v1/transaction"
    }

    @GetMapping("/history")
    override fun history(): Set<TransactionDto> {
        return transactionService.history(SecurityContextHolder.getContext().authentication.name)
    }
}
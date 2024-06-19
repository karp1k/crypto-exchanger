package testproject.cryptoexchanger.api

import testproject.cryptoexchanger.dto.TransactionDto

interface TransactionApi {

    fun history(): Set<TransactionDto>
}
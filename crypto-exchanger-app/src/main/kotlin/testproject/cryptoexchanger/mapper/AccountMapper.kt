package testproject.cryptoexchanger.mapper

import testproject.cryptoexchanger.dto.AccountDto
import testproject.cryptoexchanger.model.Account

object AccountMapper {

    fun toDto(account: Account): AccountDto {
        return AccountDto(currencyCode = account.currency.code, amount = account.amount)
    }

}
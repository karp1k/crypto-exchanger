package testproject.cryptoexchanger.repository;

import org.springframework.data.jpa.repository.JpaRepository
import testproject.cryptoexchanger.model.Currency

interface CurrencyRepository : JpaRepository<Currency, String> {

}
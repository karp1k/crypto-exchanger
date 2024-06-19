package testproject.cryptoexchanger.service.currencyrate

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import testproject.cryptoexchanger.dto.CurrencyRateDto
import testproject.cryptoexchanger.dto.CurrencyRatePairDto
import testproject.cryptoexchanger.mapper.CurrencyRateMapper
import testproject.cryptoexchanger.repository.CurrencyRateRepository
import testproject.cryptoexchanger.repository.CurrencyRepository

@Service
class CurrencyRateService(
    private val repository: CurrencyRateRepository,
    private val currencyRepository: CurrencyRepository,
) {

    @Transactional // workaround for avoiding unnecessary selects
    fun create(dto: CurrencyRateDto) {
        val entity = CurrencyRateMapper.toEntity(
            dto,
            currencyRepository.getById(dto.baseCurrencyCode),  // todo research why getReferenceById/getById hits db
            currencyRepository.getById(dto.quoteCurrencyCode) // todo research why getReferenceById/getById hits db
        )

        repository.save(entity)
    }

    fun getCurrencyPairs(): Set<CurrencyRatePairDto> {
        return repository.findDistinctCurrencyPairs().mapTo(HashSet()) {
            CurrencyRateMapper.toCurrencyRatePairDto(it)
        }
    }
}
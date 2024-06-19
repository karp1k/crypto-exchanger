package testproject.cryptoexchanger.service.transaction

import org.springframework.stereotype.Service
import testproject.cryptoexchanger.dto.TransactionDto
import testproject.cryptoexchanger.mapper.TransactionMapper
import testproject.cryptoexchanger.model.Transaction
import testproject.cryptoexchanger.repository.TransactionRepository

@Service
class TransactionService(private val transactionRepository: TransactionRepository) {

    fun createOrUpdate(transaction: Transaction): Transaction {
        return transactionRepository.save(transaction)
    }

    fun history(username: String): Set<TransactionDto> {
        return transactionRepository.findByUsername(username).mapTo(HashSet()) { TransactionMapper.toDto(it) }
    }


}
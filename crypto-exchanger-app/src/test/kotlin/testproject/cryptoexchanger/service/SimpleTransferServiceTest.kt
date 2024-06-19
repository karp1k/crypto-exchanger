package testproject.cryptoexchanger.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import testproject.cryptoexchanger.dto.UserEntityDto
import testproject.cryptoexchanger.repository.AccountRepository
import testproject.cryptoexchanger.service.user.UserEntityService
import java.util.concurrent.CountDownLatch
import java.util.concurrent.CyclicBarrier
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@SpringBootTest
internal class SimpleTransferServiceTest {

    @Autowired
    lateinit var simpleTransferService: TransferService

    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var userEntityService: UserEntityService


    @Test
    fun depositConcurrently() {
        val user = userEntityService.create(UserEntityDto("user1", "user1@sample.com", "123"))

        val count = 5
        val barrierAndLatch = prepareBarrierAndLatch(count)
        val account = user.wallets.first().walletAddresses.first().accounts.first()

        concurrentRun(
            count,
            barrierAndLatch.first,
            barrierAndLatch.second
        ) { simpleTransferService.deposit(account.id!!, BigDecimal.ONE) }
        barrierAndLatch.second.await()

        assertEquals(BigDecimal("5.00"), accountRepository.findById(account.id!!).get().amount)
    }

    @Test
    fun depositConcurrencyError() {
        val user = userEntityService.create(UserEntityDto("user2", "user2@sample.com", "123"))

        val count = 5
        val barrierAndLatch = prepareBarrierAndLatch(count)
        val account = user.wallets.first().walletAddresses.first().accounts.first()

        depositWithoutConcurrencyHandle(
            count,
            account.id!!,
            BigDecimal.ONE,
            barrierAndLatch.first,
            barrierAndLatch.second
        )
        barrierAndLatch.second.await()

        assertNotEquals(BigDecimal("5.00"), accountRepository.findById(account.id!!).get().amount)
    }

    @Test
    fun withdrawConcurrently() {
        val user = userEntityService.create(UserEntityDto("user3", "user3@sample.com", "123"))
        val count = 5
        val barrierAndLatch = prepareBarrierAndLatch(count)
        val account = accountRepository.save(user.wallets.first().walletAddresses.first().accounts.first().also { it.amount = BigDecimal(count) })

        concurrentRun(
            count,
            barrierAndLatch.first,
            barrierAndLatch.second
        ) { simpleTransferService.withdraw(account.id!!, BigDecimal.ONE) }
        barrierAndLatch.second.await()

        assertEquals(BigDecimal("0.00"), accountRepository.findById(account.id!!).get().amount)
    }

    @Test
    fun withdrawConcurrencyError() {
        val user = userEntityService.create(UserEntityDto("user4", "user4@sample.com", "123"))
        val count = 5
        val barrierAndLatch = prepareBarrierAndLatch(count)
        val account = accountRepository.save(user.wallets.first().walletAddresses.first().accounts.first().also { it.amount = BigDecimal(count) })

        withdrawWithoutConcurrencyHandle(
            count,
            account.id!!,
            BigDecimal.ONE,
            barrierAndLatch.first,
            barrierAndLatch.second
        )
        barrierAndLatch.second.await()

        assertNotEquals(BigDecimal("0.00"), accountRepository.findById(account.id!!).get().amount)
    }

    @Test
    fun transferConcurrently() {
        val user1 = userEntityService.create(UserEntityDto("user5", "user5@sample.com", "123"))
        val user2 = userEntityService.create(UserEntityDto("user6", "user6@sample.com", "123"))
        val count = 100
        val barrierAndLatch = prepareBarrierAndLatch(count)
        val fromAccount1 = accountRepository.save(user1.wallets.first().walletAddresses.first().accounts.first().also { it.amount = BigDecimal("100") })
        val toAccount1 = accountRepository.save(user2.wallets.first().walletAddresses.first().accounts.first().also { it.amount = BigDecimal("20") })

        concurrentRun(count, barrierAndLatch.first, barrierAndLatch.second) {
            simpleTransferService.transfer(
                fromAccount1.id!!,
                toAccount1.id!!,
                BigDecimal.ONE
            )
        }
        barrierAndLatch.second.await()


        assertEquals(BigDecimal("0.00"), accountRepository.findById(fromAccount1.id!!).get().amount)
        assertEquals(BigDecimal("120.00"), accountRepository.findById(toAccount1.id!!).get().amount)
    }

    @Test
    fun transferConcurrentlyWithoutDeadlock() {
        val user1 = userEntityService.create(UserEntityDto("user7", "user7@sample.com", "123"))
        val user2 = userEntityService.create(UserEntityDto("user8", "user8@sample.com", "123"))

        val count = 10
        val barrierAndLatch = prepareBarrierAndLatch(count)
        val fromAccount = accountRepository.save(user1.wallets.first().walletAddresses.first().accounts.first().also { it.amount = BigDecimal("10") })
        val toAccount = accountRepository.save(user2.wallets.first().walletAddresses.first().accounts.first().also { it.amount = BigDecimal("20") })

        concurrentRun(count / 2, barrierAndLatch.first, barrierAndLatch.second) {
            simpleTransferService.transfer(
                fromAccount.id!!,
                toAccount.id!!,
                BigDecimal.ONE
            )
        }

        concurrentRun(count / 2, barrierAndLatch.first, barrierAndLatch.second) {
            simpleTransferService.transfer(
                toAccount.id!!,
                fromAccount.id!!,
                BigDecimal("2")
            )
        }

        barrierAndLatch.second.await()

        assertEquals(BigDecimal("15.00"), accountRepository.findById(fromAccount.id!!).get().amount)
        assertEquals(BigDecimal("15.00"), accountRepository.findById(toAccount.id!!).get().amount)

    }

    fun prepareBarrierAndLatch(count: Int): Pair<CyclicBarrier, CountDownLatch> =
        Pair(CyclicBarrier(count), CountDownLatch(count))

    fun concurrentRun(times: Int, barrier: CyclicBarrier, latch: CountDownLatch, runnable: Runnable) {
        var counter = times
        while (counter != 0) {
            Thread {
                barrier.await()
                runnable.run()
                latch.countDown()
            }.start()
            counter--
        }
    }

    fun depositWithoutConcurrencyHandle(
        times: Int,
        accountId: Long,
        amount: BigDecimal,
        barrier: CyclicBarrier,
        latch: CountDownLatch
    ) {
        concurrentRun(times, barrier, latch) {
            val account = accountRepository.findById(accountId).orElseThrow { java.lang.AssertionError() }
            account.amount = account.amount.add(amount)
            accountRepository.save(account)
        }
    }

    fun withdrawWithoutConcurrencyHandle(
        times: Int,
        accountId: Long,
        amount: BigDecimal,
        barrier: CyclicBarrier,
        latch: CountDownLatch
    ) {
        concurrentRun(times, barrier, latch) {
            val account = accountRepository.findById(accountId).orElseThrow { java.lang.AssertionError() }
            account.amount = account.amount.add(amount.negate())
            accountRepository.save(account)
        }
    }


}
package testproject.cryptoexchanger.service

import org.junit.jupiter.api.Test
import java.math.BigDecimal

class Testa {

    @Test
    fun t() {
        val a = BigDecimal("0.1")
        val b = BigDecimal("-0.1")
        val c = BigDecimal("0.0")

        println(a > BigDecimal.ZERO)
        println(b > BigDecimal.ZERO)
        println(c > BigDecimal.ZERO)
        println(BigDecimal("0.00") == BigDecimal.ZERO)
        println(BigDecimal("0.00").signum() == 0)
    }

}
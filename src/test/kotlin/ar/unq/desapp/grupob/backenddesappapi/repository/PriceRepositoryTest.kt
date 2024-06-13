package ar.unq.desapp.grupob.backenddesappapi.repository

import ar.unq.desapp.grupob.backenddesappapi.helpers.DataSpringService
import ar.unq.desapp.grupob.backenddesappapi.helpers.PriceBuilder
import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.Price
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.assertTrue
import java.time.LocalDate
import java.time.LocalDateTime


@SpringBootTest
class PriceRepositoryTest {
    @Autowired
    lateinit var priceRepository : PriceRepository
    @Autowired
    lateinit var dataService: DataSpringService

    @AfterEach
    fun clean() {
        priceRepository.deleteAll()
        //dataService.cleanUp()
    }

    @Test
    fun `try to get pricing of an nonexistent crypto active returns empty`(){
        val ada = priceRepository.findByCryptoCurrency(CryptoCurrency.ADAUSDT)
        assertTrue(ada.isEmpty())
    }

    @Test
    fun `try to get an existing pricing for a given crypto active previously saved gets that pricing`(){
        val testPrice = Price(CryptoCurrency.AAVEUSDT,2.5F)
        priceRepository.save(testPrice)
        val aave = priceRepository.findByCryptoCurrencyAndPriceTimeAfter(CryptoCurrency.AAVEUSDT)
        assertEquals(CryptoCurrency.AAVEUSDT, aave.first().cryptoCurrency)
        assertEquals(2.5F, aave.first().value)
        assertEquals(1, aave.size)
    }

    @Test
    fun `get prices for a given crypto active returns prices from the last 24hs`(){
        val testPrice = PriceBuilder().withCryptoCurrency(CryptoCurrency.AAVEUSDT).withValue(2.5f).build()
        val oldPrice = PriceBuilder().withCryptoCurrency(CryptoCurrency.AAVEUSDT).withPriceTime(LocalDateTime.now().minusDays(2)).build()
        priceRepository.save(testPrice)
        priceRepository.save(oldPrice)
        val result = priceRepository.findByCryptoCurrencyAndPriceTimeAfter(CryptoCurrency.AAVEUSDT)

        val isTestPriceInResult = result.any { it.id == testPrice.id }
        val isOldPriceNotInResult = !result.any {it.id == oldPrice.id}

        assertTrue(isTestPriceInResult, "asd")
        assertTrue(isOldPriceNotInResult,"dsa")
    }

    @Test
    fun `get last price`(){
        val testPrice = PriceBuilder().withCryptoCurrency(CryptoCurrency.AAVEUSDT).withValue(2.5f).build()
        val oldPrice = PriceBuilder().withCryptoCurrency(CryptoCurrency.AAVEUSDT).withPriceTime(LocalDateTime.now().minusDays(2)).build()
        priceRepository.save(testPrice)
        priceRepository.save(oldPrice)
        val result = priceRepository.findFirstByCryptoCurrencyOrderByPriceTimeDesc(CryptoCurrency.AAVEUSDT)

        assertEquals(testPrice.id, result.id)
    }

}
package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.Price
import ar.unq.desapp.grupob.backenddesappapi.repository.PriceRepository
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance.BinanceApiService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance.BinancePriceResponse
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.dolarApi.DolarApiService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.dolarApi.DolarPriceResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootTest
class PriceServiceCacheTest {
    @MockBean
    lateinit var priceRepository: PriceRepository

    @MockBean
    lateinit var binanceApiService: BinanceApiService

    @MockBean
    lateinit var dolarApiService: DolarApiService

    @Autowired
    lateinit var priceService: PriceService

    @Autowired
    lateinit var cacheManager: CacheManager

    @BeforeEach
    fun setup() {
        // Clear cache before each test
        cacheManager.getCache("prices")?.clear()
    }
    @Test
    fun `test getAllPrices cache`() {
        // Mock repository data
        val mockPrices = listOf(Price(CryptoCurrency.BTCUSDT, 10000.0f), Price(CryptoCurrency.ETHUSDT, 2000.0f))
        `when`(priceRepository.findAll()).thenReturn(mockPrices)

        // Call the service method and verify cache
        val firstCall = priceService.getAllPrices()
        val secondCall = priceService.getAllPrices()

        // Verify that the repository method was called only once
        verify(priceRepository, times(1)).findAll()

        // Verify the data returned is correct
        assertEquals(mockPrices, firstCall)
        assertEquals(mockPrices, secondCall)
    }

    @Test
    fun `test getPrices cache`() {
        // Mock repository data
        val crypto = CryptoCurrency.BTCUSDT
        val mockPrices = listOf(Price(crypto, 10000.0f))
        `when`(priceRepository.findByCryptoCurrency(crypto)).thenReturn(mockPrices)

        // Call the service method and verify cache
        val firstCall = priceService.getPrices(crypto)
        val secondCall = priceService.getPrices(crypto)

        // Verify that the repository method was called only once
        verify(priceRepository, times(1)).findByCryptoCurrency(crypto)

        // Verify the data returned is correct
        assertEquals(mockPrices, firstCall)
        assertEquals(mockPrices, secondCall)
    }

    @Test
    fun `test getPrices cache distintos`() {
        // Mock repository data
        val crypto = CryptoCurrency.BTCUSDT
        val mockPrices = listOf(Price(crypto, 10000.0f))
        `when`(priceRepository.findByCryptoCurrency(crypto)).thenReturn(mockPrices)

        // Call the service method and verify cache
        val firstCall = priceService.getPrices(crypto)
        val thirdCall = priceService.getPrices(CryptoCurrency.ALICEUSDT)
        val secondCall = priceService.getPrices(crypto)

        // Verify that the repository method was called only once
        verify(priceRepository, times(1)).findByCryptoCurrency(crypto)

        // Verify the data returned is correct
        assertEquals(mockPrices, firstCall)
        assertEquals(mockPrices, secondCall)
        assertNotEquals(mockPrices, thirdCall)
    }

    @Test
    fun `test updatePrices updates cache`() {
        // Mock repository data
        val mockResponse = DolarPriceResponse(
            "USD","cripto","Cripto","101", "100",
            "2024-05-15T20:57:00.000Z"
        )
        val binancePrices = listOf(
            BinancePriceResponse("BTCUSDT", "10000"), BinancePriceResponse("ETHUSDT", "2000"))
        `when`(binanceApiService.getAllPrices()).thenReturn(binancePrices)
        `when`(dolarApiService.getDolarCryptoPrice()).thenReturn(mockResponse)

        // Call the service method and verify cache
        priceService.updatePrices()

        // Verify that the repository method was called
        verify(priceRepository, times(1)).saveAll(anyList())

        val names = cacheManager.cacheNames
        // Verify that the cache was updated
        val cache = cacheManager.getCache(names.first())

        val cachedPrices = cache?.get("allPrices")?.get() as List<*>

        assertNotNull(cachedPrices)
        assertEquals(3, cachedPrices.size)
    }
}

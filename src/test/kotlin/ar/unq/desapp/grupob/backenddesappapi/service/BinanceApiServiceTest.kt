package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance.BinanceApiService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance.BinancePriceResponse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.web.client.RestTemplate

@SpringBootTest
class BinanceApiServiceTest {

    @Autowired
    lateinit var binanceApiService: BinanceApiService

    @MockBean
    lateinit var restTemplateMock: RestTemplate

    @Test
    fun testGetPriceForCrypto() {
        val mockResponse = BinancePriceResponse("ALICEUSDT", "10.00")
        `when`(restTemplateMock.getForObject("https://api1.binance.com/api/v3/ticker/price?symbol=ALICEUSDT", BinancePriceResponse::class.java))
            .thenReturn(mockResponse)
        val responsePrice = binanceApiService.getPriceForCrypto("ALICEUSDT")
        assertEquals(10.0F, responsePrice!!.price.toFloat())
        assertEquals("ALICEUSDT", responsePrice.symbol)
    }

    @Test
    fun testGetPrices() {
        val symbols = listOf("ALICEUSDT", "ETHBTC")

        val mockResponse = arrayOf(
            BinancePriceResponse("ALICEUSDT", "10.00"),
            BinancePriceResponse("ETHBTC", "3000.00")
        )

        `when`(restTemplateMock.getForObject("https://api1.binance.com/api/v3/ticker/price?symbols=[\"ALICEUSDT\",\"ETHBTC\"]", Array<BinancePriceResponse>::class.java))
            .thenReturn(mockResponse)

        val prices = binanceApiService.getPrices(symbols)

        assertEquals(2, prices.size)
        assertEquals("ALICEUSDT", prices[0].symbol)
        assertEquals("10.00", prices[0].price)
        assertEquals("ETHBTC", prices[1].symbol)
        assertEquals("3000.00", prices[1].price)
    }
}

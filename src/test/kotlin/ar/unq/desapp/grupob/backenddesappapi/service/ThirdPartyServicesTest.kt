package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance.BinanceApiService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance.BinancePriceResponse
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.dolarApi.DolarApiService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.dolarApi.DolarPriceResponse
import ar.unq.desapp.grupob.backenddesappapi.utils.ApiNotResponding
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@SpringBootTest
class ThirdPartyServicesTest {

    @Autowired
    lateinit var binanceApiService: BinanceApiService

    @Autowired
    lateinit var dolarApiService: DolarApiService

    @MockBean
    lateinit var restTemplateMock: RestTemplate

    @Test
    fun testGetPriceForCryptoSuccessfully() {
        val mockResponse = BinancePriceResponse("ALICEUSDT", "10.00")
        `when`(restTemplateMock.getForObject("https://api1.binance.com/api/v3/ticker/price?symbol=ALICEUSDT", BinancePriceResponse::class.java))
            .thenReturn(mockResponse)
        val responsePrice = binanceApiService.getPriceForCrypto("ALICEUSDT")
        assertEquals(10.0F, responsePrice!!.price.toFloat())
        assertEquals("ALICEUSDT", responsePrice.symbol)
    }

    @Test
    fun testGetPricesSuccessfully() {
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

    @Test
    fun testGetPriceForCryptoWhenApiFails() {
        `when`(restTemplateMock.getForObject("https://api1.binance.com/api/v3/ticker/price?symbol=ALICEUSDT", BinancePriceResponse::class.java))
            .thenThrow(RestClientException("API is not responding"))
        val exception = assertThrows<ApiNotResponding> { binanceApiService.getPriceForCrypto("ALICEUSDT") }
        assertEquals(exception.message, "API is not responding")
    }

    @Test
    fun testGetPricesWhenApiFails() {
        val symbols = listOf("ALICEUSDT", "ETHBTC")
        //mock simulated non responsive api
        `when`(restTemplateMock.getForObject("https://api1.binance.com/api/v3/ticker/price?symbols=[\"ALICEUSDT\",\"ETHBTC\"]", Array<BinancePriceResponse>::class.java))
            .thenThrow(RestClientException("API is not responding"))

        val exception = assertThrows<ApiNotResponding> {  binanceApiService.getPrices(symbols) }

        //assertEquals(emptyList<BinancePriceResponse>(), prices)
        assertEquals("API is not responding", exception.message)
    }

    @Test
    fun testGetDolarCryptoPriceSuccessfully() {
        val mockResponse = DolarPriceResponse(
           "USD","cripto","Cripto","1078", "1095", "2024-05-15T20:57:00.000Z"
        )
        `when`(restTemplateMock.getForObject("https://dolarapi.com/v1/dolares/cripto", DolarPriceResponse::class.java))
            .thenReturn(mockResponse)
        val responsePrice = dolarApiService.getDolarCryptoPrice()!!
        assertEquals("USD" , responsePrice.moneda)
        assertEquals("cripto",responsePrice.casa)
        assertEquals("Cripto",responsePrice.nombre)
        assertEquals("1078",responsePrice.compra)
        assertEquals("1095",responsePrice.venta)
        assertEquals("2024-05-15T20:57:00.000Z",responsePrice.fechaActualizacion)
    }
}

package ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.utils.ApiNotResponding
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Service
class BinanceApiService() {

    private val baseUrl = "https://api1.binance.com/api/v3/ticker/price"

    @Autowired
    lateinit var restTemplate: RestTemplate

    fun getPriceForCrypto(symbol: String) : BinancePriceResponse?{
        val url = "$baseUrl?symbol=$symbol"
        return try {
            val response= restTemplate.getForObject(url, BinancePriceResponse::class.java)
            response
        }catch(e: RestClientException){
            //BinancePriceResponse("","") va precio vacio o tiro exepcion?
            throw ApiNotResponding()
        }
    }

    fun getPrices(symbols: List<String>): List<BinancePriceResponse> {
        val formattedSymbols = symbols.joinToString(",") { "\"$it\"" }
        val url = "$baseUrl?symbols=[$formattedSymbols]"
        return try {
            val response = restTemplate.getForObject(url, Array<BinancePriceResponse>::class.java)
            response?.toList() ?: emptyList()
        } catch (e: RestClientException) {
            //emptyList() lista vacia o tiro exepcion?
            throw ApiNotResponding()

        }
    }

    fun getAllPrices(): List<BinancePriceResponse>{
        val symbolList = CryptoCurrency.entries.map{it.name}
        return getPrices(symbolList.filterNot{it=="USDAR"})
    }
}

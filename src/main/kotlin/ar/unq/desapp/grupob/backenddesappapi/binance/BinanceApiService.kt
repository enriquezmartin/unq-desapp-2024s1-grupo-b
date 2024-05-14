package ar.unq.desapp.grupob.backenddesappapi.binance

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class BinanceApiService() {

    @Autowired
    lateinit var restTemplate: RestTemplate

    fun getPrices(symbols: List<String>): List<BinancePriceResponse> {
        val baseUrl = "https://api1.binance.com/api/v3/ticker/price"
        val url = "$baseUrl?symbols=${symbols.joinToString(",")}"

        val response = restTemplate.getForObject(url, Array<BinancePriceResponse>::class.java)

        return response?.toList() ?: emptyList()
    }

    fun getAllPrices(): List<BinancePriceResponse>{
        val symbolList = CryptoCurrency.entries.map{it.name}

        return getPrices(symbolList)
    }
}
/*como hacer la actualizacion cada 10 min tentantivo

agregar @EnableScheduling a la clase de BackendDesappApiApplication

@Component
class PriceUpdater(private val binanceApiService: BinanceApiService) {

    @Scheduled(fixedRate = 600000) // Actualizar cada 10 minutos (en milisegundos)
    fun updatePrices() {
        val prices = getAllPrices()

        // persistir los valores
    }
}
 */

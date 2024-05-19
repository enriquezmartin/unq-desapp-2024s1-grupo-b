package ar.unq.desapp.grupob.backenddesappapi.thirdApiService.dolarApi

import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance.BinancePriceResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class DolarApiService {

    private val baseUrl = "https://dolarapi.com/v1/dolares/cripto"

    @Autowired
    lateinit var restTemplate: RestTemplate

    fun getDolarCryptoPrice(): DolarPriceResponse?{
        return restTemplate.getForObject(baseUrl, DolarPriceResponse ::class.java)
    }
}
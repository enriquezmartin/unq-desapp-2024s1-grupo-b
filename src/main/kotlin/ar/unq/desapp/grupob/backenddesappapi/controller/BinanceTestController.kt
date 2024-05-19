package ar.unq.desapp.grupob.backenddesappapi.controller

import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance.BinanceApiService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance.BinancePriceResponse
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.dolarApi.DolarApiService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.dolarApi.DolarPriceResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BinanceTestController {

    @Autowired
    lateinit var service: BinanceApiService
    @Autowired
    lateinit var dolarService: DolarApiService


    @GetMapping("/binance")
    fun asd(): List<BinancePriceResponse>{
        return service.getPrices(listOf("ALICEUSDT", "ETHBTC"))
    }
    @GetMapping("/dolarapi")
    fun dolarGet(): DolarPriceResponse?{
        return dolarService.getDolarCryptoPrice()
    }


}
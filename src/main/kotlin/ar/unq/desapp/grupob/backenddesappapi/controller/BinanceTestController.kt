package ar.unq.desapp.grupob.backenddesappapi.controller

import ar.unq.desapp.grupob.backenddesappapi.binance.BinanceApiService
import ar.unq.desapp.grupob.backenddesappapi.binance.BinancePriceResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BinanceTestController {

    @Autowired
    lateinit var service: BinanceApiService

    @GetMapping("/binance")
    fun asd(): BinancePriceResponse{
        return service.getPrices(listOf("ALICEUSDT"))[0]
    }
}
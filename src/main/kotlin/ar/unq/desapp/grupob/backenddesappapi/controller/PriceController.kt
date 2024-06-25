package ar.unq.desapp.grupob.backenddesappapi.controller

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.Price
import ar.unq.desapp.grupob.backenddesappapi.service.PriceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PriceController {

    @Autowired
    lateinit var priceService: PriceService

    @GetMapping("/getPrices")
    fun getPrices(): List<Price>{
        return priceService.getAllPrices()
    }
    @GetMapping("/getPrices/{crypto}")
    fun getPrices(@PathVariable crypto: String): List<Price>{
        return priceService.getPrices(CryptoCurrency.valueOf(crypto))
    }
    @GetMapping("/getLatestPrices/{crypto}")
    fun getLatestPrices(@PathVariable crypto: String): List<Price> {
        return priceService.getLatestPrices(CryptoCurrency.valueOf(crypto))
    }
}
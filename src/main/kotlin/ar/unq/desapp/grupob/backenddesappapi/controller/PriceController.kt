package ar.unq.desapp.grupob.backenddesappapi.controller

import ar.unq.desapp.grupob.backenddesappapi.model.Price
import ar.unq.desapp.grupob.backenddesappapi.service.PriceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PriceController {

    @Autowired
    lateinit var priceService: PriceService

    @GetMapping("/getPrices")
    fun getPrices(): List<Price>{
        return priceService.getAllPrices()
    }

    @PostMapping("/updatePrices")
    fun updatePrices(): String{
        try{
            priceService.updatePrices()
            return "todo salio bonito"
        } catch (e: Exception){
            return "Ay fallo"
        }

    }

}
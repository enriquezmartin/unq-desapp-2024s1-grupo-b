package ar.unq.desapp.grupob.backenddesappapi.service.impl

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.Price
import ar.unq.desapp.grupob.backenddesappapi.repository.PriceRepository
import ar.unq.desapp.grupob.backenddesappapi.service.PriceService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance.BinanceApiService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.dolarApi.DolarApiService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Transactional
@Service
class PriceServiceImpl : PriceService{

    private var cryptoCurrencies = CryptoCurrency.values()

    @Autowired
    lateinit var priceRepository: PriceRepository

    @Autowired
    lateinit var binanceApiService: BinanceApiService

    @Autowired
    lateinit var dolarApiService: DolarApiService
    override fun updatePrices() {
        val binancePrices = binanceApiService.getAllPrices()
        val dolarApiResp = dolarApiService.getDolarCryptoPrice()

        // Obtener todos los precios existentes
        val existingPrices = priceRepository.findAll().associateBy { it.cryptoCurrency }

        // Crear una lista mutable para los precios actualizados o nuevos
        val prices = binancePrices.map {
            val cryptoCurrency = CryptoCurrency.valueOf(it.symbol)
            val price = existingPrices[cryptoCurrency]?.apply {
                this.value = it.price.toFloat()
                this.priceTime = LocalDate.now()
            } ?: Price(cryptoCurrency, it.price.toFloat())
            price
        }.toMutableList()

        // Manejar el precio del dólar
        val dolarPrice = existingPrices[CryptoCurrency.USDAR]?.apply {
            this.value = dolarApiResp!!.venta.toFloat()
            this.priceTime = LocalDate.now()
        } ?: Price(CryptoCurrency.USDAR, dolarApiResp!!.venta.toFloat())

        prices.add(dolarPrice)

        // Guardar todos los precios actualizados o nuevos
        priceRepository.saveAll(prices)
    }

    override fun getAllPrices(): List<Price> {
        return priceRepository.findAll()
    }


}
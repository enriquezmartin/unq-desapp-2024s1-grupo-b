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
        val prices = binancePrices.map {
            Price(CryptoCurrency.valueOf(it.symbol), it.price.toFloat())
        }
        priceRepository.saveAll(prices)
    }

    override fun getAllPrices(): List<Price> {
        return priceRepository.findAll()
    }


}
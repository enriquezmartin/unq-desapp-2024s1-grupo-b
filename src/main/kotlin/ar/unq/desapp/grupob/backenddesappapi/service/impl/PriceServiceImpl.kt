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
        val dolarApiResp = dolarApiService.getDolarCryptoPrice()
        val dolarPrice = Price(CryptoCurrency.USDAR, dolarApiResp!!.venta.toFloat())
        prices.toMutableList().add(dolarPrice)
        priceRepository.saveAll(prices)
    }

    override fun getAllPrices(): List<Price> {
        return priceRepository.findAll()
    }

    override fun getPrices(cryptoCurrency: CryptoCurrency): List<Price> {
        return priceRepository.findByCryptoCurrency(cryptoCurrency)
    }


    override fun getLatestPrices(cryptoCurrency: CryptoCurrency): List<Price> {
        return priceRepository.findByCryptoCurrencyAndPriceTimeAfter(cryptoCurrency)
    }

    override fun getLastPrice(cryptoCurrency: CryptoCurrency): Price {
        return priceRepository.findFirstByCryptoCurrencyOrderByPriceTimeDesc(cryptoCurrency)
    }
}
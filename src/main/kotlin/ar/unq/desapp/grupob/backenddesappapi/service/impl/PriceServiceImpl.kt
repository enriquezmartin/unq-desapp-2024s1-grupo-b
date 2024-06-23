package ar.unq.desapp.grupob.backenddesappapi.service.impl

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.Price
import ar.unq.desapp.grupob.backenddesappapi.repository.PriceRepository
import ar.unq.desapp.grupob.backenddesappapi.service.PriceService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance.BinanceApiService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.dolarApi.DolarApiService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CachePut
import org.springframework.stereotype.Service
import org.springframework.cache.annotation.Cacheable

@Transactional
@Service
@CacheConfig(cacheNames = ["prices"])
class PriceServiceImpl : PriceService{
    @Autowired
    lateinit var priceRepository: PriceRepository
    @Autowired
    lateinit var binanceApiService: BinanceApiService
    @Autowired
    lateinit var dolarApiService: DolarApiService

    @CachePut(cacheNames = ["prices"], key = "'allPrices'", condition = "True")
    override fun updatePrices(): List<Price> {
        var updatedPriceList = mutableListOf<Price>()
        val binancePrices = binanceApiService.getAllPrices().map {
            Price(CryptoCurrency.valueOf(it.symbol), it.price.toFloat())
        }
        updatedPriceList.addAll(binancePrices)
        val dolarApiResp = dolarApiService.getDolarCryptoPrice()
        val dolarPrice = Price(CryptoCurrency.USDAR, dolarApiResp!!.venta.toFloat())
        updatedPriceList.add(dolarPrice)
        priceRepository.saveAll(updatedPriceList)
        return updatedPriceList
    }

    @Cacheable(cacheNames = ["prices"], key="'allPrices'")
    override fun getAllPrices(): List<Price> {
        return priceRepository.findAll()
    }

    @Cacheable(cacheNames = ["prices"], key="'prices_'+#cryptoCurrency")
    override fun getPrices(cryptoCurrency: CryptoCurrency): List<Price> {
        return priceRepository.findByCryptoCurrency(cryptoCurrency)
    }

    //@Cacheable("prices", key="#cryptoCurrency")
    override fun getLatestPrices(cryptoCurrency: CryptoCurrency): List<Price> {
        return priceRepository.findByCryptoCurrencyAndPriceTimeAfter(cryptoCurrency)
    }
}
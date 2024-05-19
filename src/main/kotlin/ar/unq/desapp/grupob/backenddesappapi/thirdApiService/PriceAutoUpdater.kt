package ar.unq.desapp.grupob.backenddesappapi.thirdApiService

import ar.unq.desapp.grupob.backenddesappapi.service.PriceService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PriceAutoUpdater {
    @Autowired
    lateinit var priceService : PriceService

    private val logger = LoggerFactory.getLogger(PriceAutoUpdater::class.java)

    @Scheduled(fixedRate = 45000) // Actualizar cada 10 minutos (en milisegundos)
    fun updatePrices() {
        logger.info("Updating prices...")
        priceService.updatePrices()
        logger.info("Prices were updated.")
    }
}
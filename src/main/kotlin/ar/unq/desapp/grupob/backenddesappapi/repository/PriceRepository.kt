package ar.unq.desapp.grupob.backenddesappapi.repository

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.Price
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

@Repository
interface PriceRepository : JpaRepository<Price, Long>  {
    fun findByCryptoCurrency(crypto: CryptoCurrency) : List<Price>
    fun findByCryptoCurrencyAndPriceTimeAfter(cryptoCurrency: CryptoCurrency, priceTimeAfter: LocalDateTime? = LocalDateTime.now().minusDays(1)) : List<Price>
    fun findFirstByCryptoCurrencyOrderByPriceTimeDesc(crypto: CryptoCurrency): Price

}

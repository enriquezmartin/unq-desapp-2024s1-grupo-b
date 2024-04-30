package ar.unq.desapp.grupob.backenddesappapi.repository

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.Price
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.assertTrue


@SpringBootTest
class PriceRepositoryTest {
    @Autowired
    lateinit var priceRepository : PriceRepository

    @Test
    fun `try to get pricing of an nonexistent crypto active returns empty`(){
        val ada = priceRepository.findByCryptoCurrency(CryptoCurrency.ADAUSDT)
        assertTrue(ada.isEmpty())
    }

    @Test
    fun `try to get an existing pricing for a given crypto active previously saved gets that pricing`(){
        val testPrice = Price(CryptoCurrency.AAVEUSDT,2.5F)
        priceRepository.save(testPrice)
        val aave = priceRepository.findByCryptoCurrency(CryptoCurrency.AAVEUSDT)
        assertEquals(CryptoCurrency.AAVEUSDT, aave.first().cryptoCurrency)
        assertEquals(2.5F, aave.first().value)
        assertEquals(1, aave.size)
    }
/* falta crear un JDBCTemplate
    fun cleanup(){
        //1° Traer todas las tablas
        val tables = jdbcTemplate.queryForList("SHOW TABLES")
        val tableNames = tables.map { it.values.first() as String }
        //2° Desactivar el chequeo por FK
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0")
        //3° Vaciar todas las tablas
        tableNames.forEach{tableName ->
            jdbcTemplate.execute("TRUNCATE TABLE $tableName")
        }
        //4° Activar el chequeo por FK
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1")
    }
*/
}
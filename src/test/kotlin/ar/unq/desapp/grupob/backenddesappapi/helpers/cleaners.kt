package ar.unq.desapp.grupob.backenddesappapi.helpers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DataSpringService(private val jdbcTemplate: JdbcTemplate) {
    fun cleanUp() {
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
}
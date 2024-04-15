package ar.unq.desapp.grupob.backenddesappapi.DTO

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.OperationType
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate

data class IntentItem(
    val createdDate: LocalDate,
    val cryptoCurrency: CryptoCurrency,
    val operationType: OperationType,
    val amount: Float,
    val pricePerCryptoCurrency: Float,
    val princeInArs: Float,
    val user: String,
    val operations: Int,
    val reputation: String,
)
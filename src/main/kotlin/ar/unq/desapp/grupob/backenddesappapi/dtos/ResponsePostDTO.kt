package ar.unq.desapp.grupob.backenddesappapi.dtos

import java.time.LocalDate
import java.time.LocalDateTime

data class ResponsePostDTO(
    val id: Long,
    val cryptoCurrency: String,
    val amount: Float,
    val price: Float,
    val user: UserDTO,
    val operation: String,
    val createdDate: LocalDateTime
){
    var priceInArs: Float? = null
}
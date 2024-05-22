package ar.unq.desapp.grupob.backenddesappapi.dtos

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency

data class PostDTO(
    val price: Float,
    val amount: Float,
    val cryptoCurrency: String,
    val operation: String
)
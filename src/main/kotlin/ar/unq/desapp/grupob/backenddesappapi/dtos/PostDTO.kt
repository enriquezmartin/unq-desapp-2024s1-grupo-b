package ar.unq.desapp.grupob.backenddesappapi.dtos

data class PostDTO(
    val price: Float,
    val amount: Float,
    val cryptoCurrency: String,
    val operation: String
)
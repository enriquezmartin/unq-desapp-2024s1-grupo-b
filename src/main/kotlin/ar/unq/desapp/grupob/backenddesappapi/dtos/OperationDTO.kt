package ar.unq.desapp.grupob.backenddesappapi.dtos

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency

data class OperationDTO(
    val cryptoActive: CryptoCurrency,
    val nominalQuantity: Float,
    val currencyPrice: Float,
    val amount: Float,
    val user: String,
    val numberOfOperations: Int,
    val reputation: Int,
    val shippingAddress: String?,
)
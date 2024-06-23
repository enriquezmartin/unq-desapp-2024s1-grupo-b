package ar.unq.desapp.grupob.backenddesappapi.dtos

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency

data class OperationReportDTO(
    val cryptoActive: CryptoCurrency,
    val amount: Float,
    val priceInDollars: Float,
    val priceInArs: Float
)

package ar.unq.desapp.grupob.backenddesappapi.thirdApiService.dolarApi

data class DolarPriceResponse(
    val moneda: String,
    val casa: String,
    val nombre: String,
    val compra: String,
    val venta: String,
    val fechaActualizacion: String
)
package ar.unq.desapp.grupob.backenddesappapi.binance

data class BinancePriceResponse ( //entiendo que binance responde de esta forma
    val symbol: String,
    val price: String
)
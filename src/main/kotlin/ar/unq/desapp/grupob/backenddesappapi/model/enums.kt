package ar.unq.desapp.grupob.backenddesappapi.model

enum class CryptoCurrency {
    ALICEUSDT, MATICUSDT, AXSUSDT, AAVEUSDT,
    ATOMUSDT, NEOUSDT, DOTUSDT, ETHUSDT,
    CAKEUSDT, BTCUSDT, BNBUSDT, ADAUSDT,
    TRXUSDT, AUDIOUSDT, USDAR //Como le pongo al dolar cripto?
}
enum class OperationType {
    PURCHASE, SALE
}
enum class StatusPost{
    ACTIVE, CLOSED, IN_PROGRESS
}
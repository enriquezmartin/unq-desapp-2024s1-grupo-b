package ar.unq.desapp.grupob.backenddesappapi.model

class Post(val cryptoCurrency: CryptoCurrency, val amount: Float, val price: Price, val user: UserEntity, val operationType: OperationType){

    val priceInArs: Float
        get() {
            return amount * price.value
        }
}
package ar.unq.desapp.grupob.backenddesappapi.model

import java.time.LocalDate

class Post(val cryptoCurrency: CryptoCurrency, val amount: Float, val price: Float, val operationType: OperationType){

    val priceInArs: Float
        get() {
            return amount * price
        }

    var user: UserEntity? = null

    var createdDate: LocalDate? = null
}
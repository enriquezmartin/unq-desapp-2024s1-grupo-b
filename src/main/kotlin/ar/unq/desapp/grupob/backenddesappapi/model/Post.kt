package ar.unq.desapp.grupob.backenddesappapi.model

import java.time.LocalDate

class Post(var id: Long?, val cryptoCurrency: CryptoCurrency, val amount: Float, val price: Float, val operationType: OperationType){

    val priceInArs: Float
        get() {
            return amount * price
        }

    var user: UserEntity? = null
    var createdDate: LocalDate? = null
    var status: StatusPost? = null

    fun getType(): OperationType = operationType
}
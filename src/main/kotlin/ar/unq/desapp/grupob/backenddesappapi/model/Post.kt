package ar.unq.desapp.grupob.backenddesappapi.model

import jakarta.persistence.*
import jdk.jfr.DataAmount
import java.time.LocalDate

@Entity
class Post(){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var cryptoCurrency: CryptoCurrency?= null
    var amount: Float?= null
    var price: Float?= null
    var operationType: OperationType?= null
    //@ManyToOne
    //var user: UserEntity? = null
    var createdDate: LocalDate? = null
    var status: StatusPost? = null
    constructor(cryptoCurrency: CryptoCurrency, amount: Float, price: Float, operationType: OperationType) : this(){
        this.cryptoCurrency = cryptoCurrency
        this.amount = amount
        this.price = price
        this.operationType = operationType
        this.createdDate = LocalDate.now()
        this.status = StatusPost.ACTIVE
    }
}
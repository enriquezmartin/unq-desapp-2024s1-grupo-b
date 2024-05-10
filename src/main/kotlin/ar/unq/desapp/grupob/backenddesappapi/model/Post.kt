package ar.unq.desapp.grupob.backenddesappapi.model

import jakarta.persistence.*
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
    var createdDate: LocalDate? = null
    var status: PostStatus? = null
    constructor(cryptoCurrency: CryptoCurrency,
                amount: Float,
                price: Float,
                operationType: OperationType,
                status: PostStatus = PostStatus.ACTIVE) : this(){
        this.cryptoCurrency = cryptoCurrency
        this.amount = amount
        this.price = price
        this.operationType = operationType
        this.createdDate = LocalDate.now()
        this.status = status
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    var owner: UserEntity? = null

}
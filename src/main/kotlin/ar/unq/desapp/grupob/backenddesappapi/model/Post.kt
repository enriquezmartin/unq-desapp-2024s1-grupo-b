package ar.unq.desapp.grupob.backenddesappapi.model

import ar.unq.desapp.grupob.backenddesappapi.dtos.PostDTO
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Post(){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var cryptoCurrency: CryptoCurrency?= null
    var amount: Float?= null
    var price: Float?= null
    var operationType: OperationType?= null
    var createdDate: LocalDateTime? = LocalDateTime.now()
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
        this.status = status
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    var owner: UserEntity? = null

    fun toDTO() : PostDTO {
        return PostDTO(
            price = this.price!!,
            amount = this.amount!!,
            cryptoCurrency = this.cryptoCurrency!!.name,
            operation = this.operationType.toString()
        )
    }
}
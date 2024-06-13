package ar.unq.desapp.grupob.backenddesappapi.model

import ar.unq.desapp.grupob.backenddesappapi.utils.InvalidUserForPaymentException
import ar.unq.desapp.grupob.backenddesappapi.utils.PriceOutOfRangeException
import ar.unq.desapp.grupob.backenddesappapi.utils.UnavailablePostException
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Entity
class CryptoOperation(){

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long? = null
    var dateTime: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
    var status: OperationStatus = OperationStatus.IN_PROGRESS

    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "post_id", referencedColumnName = "Id")
     var post: Post? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var client: UserEntity? = null

    constructor(post: Post, userEntity: UserEntity): this(){
        this.post = post
        this.client = userEntity
    }

    companion object {
        fun initOperation(post: Post, price: Price, user: UserEntity): CryptoOperation {
            validatePrice(post, price)
            validatePost(post)
            validateUser(user, post)
            post.status = PostStatus.IN_PROGRESS
            return CryptoOperation(post, user)
        }

        private fun validateUser(user: UserEntity, post: Post) {
            if(user.id!! == post.owner!!.id) throw InvalidUserForPaymentException()
        }

        private fun validatePost(post: Post) {
            when(post.status) {
                PostStatus.CLOSED, PostStatus.IN_PROGRESS -> throw UnavailablePostException()
                else -> return
            }
        }
        private fun validatePrice(post: Post, price: Price){
            when(post.operationType) {
                OperationType.PURCHASE -> if(post.price!!  < price.value!! * 0.95) throw PriceOutOfRangeException()
                OperationType.SALE     -> if(price.value!! < post.price!!  * 0.95) throw PriceOutOfRangeException()
                else -> throw Exception("")
            }
        }
    }
}
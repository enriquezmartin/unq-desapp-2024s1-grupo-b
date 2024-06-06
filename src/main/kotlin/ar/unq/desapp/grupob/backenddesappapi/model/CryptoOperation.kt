package ar.unq.desapp.grupob.backenddesappapi.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
class CryptoOperation(){

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long? = null
    lateinit var dateTime: LocalDateTime
    lateinit var status: OperationStatus

    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "post_id", referencedColumnName = "Id")
     var post: Post? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var client: UserEntity? = null

    constructor(date: LocalDateTime, status: OperationStatus, post: Post, userEntity: UserEntity): this(){
        this.dateTime = date
        this.status = status
        this.post = post
        this.client = userEntity
    }
}
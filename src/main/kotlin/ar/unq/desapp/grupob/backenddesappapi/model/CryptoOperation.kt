package ar.unq.desapp.grupob.backenddesappapi.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class CryptoOperation(){

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long? = null
    lateinit var dateTime: LocalDate
    lateinit var status: OperationStatus

    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "post_id", referencedColumnName = "Id")
    lateinit var post: Post

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    lateinit var client: UserEntity

    constructor(date: LocalDate, status: OperationStatus, post: Post, userEntity: UserEntity): this(){
        this.dateTime = date
        this.status = status
        this.post = post
        this.client = userEntity
    }
}
package ar.unq.desapp.grupob.backenddesappapi.model

import ar.unq.desapp.grupob.backenddesappapi.utils.UserValidator
import jakarta.persistence.*
import java.lang.Integer.max
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.min

@Entity
@Table(name = "users")
class UserEntity(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var name: String? = null
    var surname: String? = null

    @Column(nullable = false, unique = true)
    var email: String? = null
    var address: String? = null
    var password: String? = null
    var cvu: String? = null

    @Column(nullable = false, unique = true)
    var walletAddress: String? = null

    var successfulOperation: Int = 0
    var score: Int = 0

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.MERGE], orphanRemoval = true)
    val intents: MutableList<Post> = mutableListOf()

    @OneToMany(mappedBy = "client", cascade = [CascadeType.MERGE])
    val operation: MutableList<CryptoOperation> = mutableListOf()

    constructor(email: String?, password: String?, name: String?, surname: String?, address: String?, cvu: String?,walletAddress: String?): this(){
        val rangeErrorMsg:(property: String) -> String = { property -> "The $property is too short or too long" }
        val emailPattern = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
        val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{6,}\$")
        val cvuPattern = Regex("^\\d{22}$")
        val walletAddressPattern = Regex("^\\d{8}$")

        this.name =UserValidator.validateRange(name, 3, 30, rangeErrorMsg(UserEntity::name.name))
        this.surname =UserValidator.validateRange(surname, 3, 30,  rangeErrorMsg(UserEntity::surname.name))
        this.email =UserValidator.validatePattern(email, emailPattern, "The email does not have a valid format")
        this.address =UserValidator.validateRange(address, 10, 30, rangeErrorMsg(UserEntity::address.name))
        this.password =UserValidator.validatePattern(password, passwordPattern, "The password is too weak")
        this.cvu =UserValidator.validatePattern(cvu, cvuPattern, "The cvu must have 22 digits")
        this.walletAddress =UserValidator.validatePattern(walletAddress, walletAddressPattern, "The wallet address must have 8 digits")
    }

    fun addPost(post: Post) {
        post.status = PostStatus.ACTIVE
        intents.add(post)
        post.owner = this
    }

    fun confirm(operation: CryptoOperation): CryptoOperation {
        operation.confirm(id!!)
        val score = if (isWithinMinutes(LocalDateTime.now(), operation.dateTime, 30)) 10 else 5
        this.score += score
        this.successfulOperation ++

        operation.client!!.score += score
        operation.client!!.successfulOperation ++

        return operation
    }

    private fun isWithinMinutes(dateTime1: LocalDateTime, dateTime2: LocalDateTime, minutes: Int): Boolean {
        val duration = Duration.between(dateTime1, dateTime2).abs()
        return duration.toMinutes() <= minutes
    }

    fun cancel(operation: CryptoOperation): CryptoOperation {
        operation.cancel(id!!)
        val newScore = max(0, score - 20)
        score = newScore
        return operation
    }

}
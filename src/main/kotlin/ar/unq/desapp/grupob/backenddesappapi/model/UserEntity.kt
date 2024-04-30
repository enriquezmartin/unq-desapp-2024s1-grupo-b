package ar.unq.desapp.grupob.backenddesappapi.model

import ar.unq.desapp.grupob.backenddesappapi.utils.UserValidator
import jakarta.persistence.*

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

    var operations: Int = 0
    var points: Int = 0

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


//    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity :: class, cascade = arrayOf(CascadeType.PERSIST))
//    @JoinTable(name = "user_profile",
//        joinColumns = [JoinColumn(name = "user_id")],
//        inverseJoinColumns = [JoinColumn(name = "profile_id")])
//    var profile: Set<Profile> = HashSet()

}
package ar.unq.desapp.grupob.backenddesappapi.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "users")

class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @NotNull
    @Column(nullable = false)
    lateinit var username: String
    lateinit var password: String
    /*lateinit var name: String
    lateinit var surname: String
    lateinit var email: String
    lateinit var address: String
    lateinit var cvuMP: String
    lateinit var walletAddress: String
*/
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity :: class, cascade = arrayOf(CascadeType.PERSIST))
    @JoinTable(name = "user_profile",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "profile_id")])
    var profile: Set<Profile> = HashSet()

}
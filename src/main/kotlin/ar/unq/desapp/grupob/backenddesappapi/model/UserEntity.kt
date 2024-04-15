package ar.unq.desapp.grupob.backenddesappapi.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @NotNull
    @Column(nullable = false, unique = true)
    var username: String?,
    var password: String?,
    var name: String?,
    var surname: String?,
    var email: String?,
    var address: String?,
    var cvu: String?,
    var walletAddress: String?,
    var operations: Int = 0,
    var points: Int = 0
){



    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity :: class, cascade = arrayOf(CascadeType.PERSIST))
    @JoinTable(name = "user_profile",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "profile_id")])
    var profile: Set<Profile> = HashSet()

}
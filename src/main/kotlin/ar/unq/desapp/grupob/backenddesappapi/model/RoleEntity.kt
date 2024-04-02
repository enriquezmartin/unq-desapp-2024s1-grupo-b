package ar.unq.desapp.grupob.backenddesappapi.model

import jakarta.persistence.*


@Entity
class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Enumerated(EnumType.STRING)
    lateinit var role: Profile

}
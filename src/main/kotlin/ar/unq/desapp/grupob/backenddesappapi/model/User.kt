package ar.unq.desapp.grupob.backenddesappapi.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "users")

class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @NotNull
    @Column(nullable = false)
    lateinit var name: String

    lateinit var surname: String
    lateinit var email: String
    lateinit var address: String
    lateinit var password: String
    lateinit var cvuMP: String
    lateinit var walletAddress: String




}
package ar.unq.desapp.grupob.backenddesappapi.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Price(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Enumerated(EnumType.STRING)
    var cryptoCurrency: CryptoCurrency? = null
    var priceTime: LocalDateTime = LocalDateTime.now()
    @Column(name = "crypto_value") //value es palabra reservada de H2SQL
    var value: Float? = null

    constructor(cryptoCurrency: CryptoCurrency, value: Float): this(){
        this.cryptoCurrency = cryptoCurrency
        this.value = value
        this.priceTime = LocalDateTime.now()
    }

}
package ar.unq.desapp.grupob.backenddesappapi.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Price(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Enumerated(EnumType.STRING)
    var cryptoCurrency: CryptoCurrency? = null
    var priceTime: LocalDate? = null
    @Column(name = "crypto_value") //value es palabra reservada de H2SQL
    var value: Float? = null

    constructor(cryptoCurrency: CryptoCurrency, value: Float): this(){
        this.cryptoCurrency = cryptoCurrency
        this.value = value
        this.priceTime = LocalDate.now()
    }

    constructor(cryptoCurrency: CryptoCurrency, value: Float, priceTime: LocalDate): this(){
        this.cryptoCurrency = cryptoCurrency
        this.value = value
        this.priceTime = priceTime
    }
}
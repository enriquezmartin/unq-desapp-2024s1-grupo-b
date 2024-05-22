package ar.unq.desapp.grupob.backenddesappapi.helpers

import ar.unq.desapp.grupob.backenddesappapi.model.*
import java.time.LocalDate

class PriceBuilder(){
    private var id: Long? = 1L
    private var cryptoCurrency: CryptoCurrency = CryptoCurrency.ALICEUSDT
    private var priceTime: LocalDate = LocalDate.now()
    private var value: Float = 10F

    fun build(): Price {
        val price = Price(cryptoCurrency, value, priceTime)
        price.id
        return price
    }
    fun withId(id: Long): PriceBuilder{
        this.id = id
        return this
    }
    fun withCryptoCurrency(currency: CryptoCurrency): PriceBuilder {
        this.cryptoCurrency = currency
        return this
    }
    fun withPriceTime(priceTime: LocalDate): PriceBuilder {
        this.priceTime = priceTime
        return this
    }

    fun withValue(value: Float): PriceBuilder{
        this.value = value
        return this
    }
}

class PostBuilder(){
    private var id: Long? = 1L
    private var cryptoCurrency: CryptoCurrency = CryptoCurrency.ALICEUSDT
    private var amount: Float = 1F
    private var price: Float = 10F
    private var operationType: OperationType = OperationType.SALE
    private var createdDate: LocalDate = LocalDate.now()
    private var status: PostStatus = PostStatus.ACTIVE
    private var user: UserEntity? = null

    fun build(): Post{
        var post = Post(cryptoCurrency, amount, price, operationType, status)
        post.id = id
        post.createdDate = createdDate
        post.owner = user
        return post
    }
    fun withId(id: Long?): PostBuilder{
        this.id = id
        return this
    }

    fun withCryptoCurrency(cryptoCurrency: CryptoCurrency): PostBuilder {
        this.cryptoCurrency = cryptoCurrency
        return this
    }

    fun withAmount(amount: Float): PostBuilder {
        this.amount = amount
        return this
    }

    fun withPrice(price: Float): PostBuilder {
        this.price = price
        return this
    }

    fun withOperationType(operationType: OperationType): PostBuilder {
        this.operationType = operationType
        return this
    }

    fun withCreatedDate(createdDate: LocalDate): PostBuilder {
        this.createdDate = createdDate
        return this
    }

    fun withStatus(status: PostStatus): PostBuilder {
        this.status = status
        return this
    }

    fun withUser(user: UserEntity?): PostBuilder {
        this.user = user
        return this
    }

}

class UserBuilder(){

    private var id: Long? = 1L
    private var name: String? = "a valid name"
    private var address: String? = "a valid address"
    private var surname: String? = "valid surname"
    private var email: String? = "email@valid.com"
    private var password: String? = "Pass*_word"
    private var cvuMP: String? = "1234567890123456789012"
    private var walletAddress: String? = "12345678"

    fun build(): UserEntity{
        val user: UserEntity = UserEntity(email, password, name, surname, address, cvuMP, walletAddress)
        user.id = this.id
        return user
    }

    fun withId(id: Long): UserBuilder{
        this.id = id
        return this
    }

    fun withName(name: String): UserBuilder {
        this.name = name
        return this
    }


    fun withAddress(address: String): UserBuilder {
        this.address = address
        return this
    }

    fun withSurname(surname: String): UserBuilder {
        this.surname = surname
        return this
    }

    fun withEmail(email: String): UserBuilder {
        this.email = email
        return this
    }

    fun withPassword(password: String): UserBuilder {
        this.password = password
        return this
    }

    fun withCvuMP(cvuMP: String): UserBuilder {
        this.cvuMP = cvuMP
        return this
    }

    fun withWalletAddress(walletAddress: String): UserBuilder {
        this.walletAddress = walletAddress
        return this
    }

}
package ar.unq.desapp.grupob.backenddesappapi.helpers

import ar.unq.desapp.grupob.backenddesappapi.model.*
import org.springframework.security.core.userdetails.User
import java.time.LocalDate
import java.time.LocalDateTime

class OperationBuilder(){
    private var id: Long? = null
    private var dateTime: LocalDateTime = LocalDateTime.now()
    private var status: OperationStatus = OperationStatus.CANCELLED
    private var post: Post? = null
    private var client: UserEntity? = null

    fun build(): CryptoOperation{
        val operation = CryptoOperation(post!!, client!!)
        operation.id = id!!
        operation.status = status
        operation.dateTime = dateTime
        return operation
    }
    fun withId(id: Long): OperationBuilder {
        this.id = id
        return this
    }
    fun withDateTime(date: LocalDateTime): OperationBuilder{
        this.dateTime = date
        return this
    }
    fun withStatus(status: OperationStatus): OperationBuilder{
        this.status = status
        return this
    }
    fun withPost(post: Post): OperationBuilder{
        this.post = post
        return this
    }
    fun withClient(client: UserEntity): OperationBuilder{
        this.client = client
        return this
    }
}
class PriceBuilder(){
    private var id: Long? = null
    private var cryptoCurrency: CryptoCurrency = CryptoCurrency.ALICEUSDT
    private var priceTime: LocalDateTime = LocalDateTime.now()
    private var value: Float = 10F

    fun build(): Price {
        val price = Price(cryptoCurrency, value)
        price.id = id
        price.priceTime = priceTime
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
    fun withPriceTime(priceTime: LocalDateTime): PriceBuilder {
        this.priceTime = priceTime
        return this
    }

    fun withValue(value: Float): PriceBuilder{
        this.value = value
        return this
    }
}

class PostBuilder(){
    private var id: Long? = null
    private var cryptoCurrency: CryptoCurrency = CryptoCurrency.ALICEUSDT
    private var amount: Float = 1F
    private var price: Float = 10F
    private var operationType: OperationType = OperationType.SALE
    private var createdDate: LocalDateTime = LocalDateTime.now()
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

    fun withCreatedDate(createdDate: LocalDateTime): PostBuilder {
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

    private var id: Long? = null
    private var name: String? = "a valid name"
    private var address: String? = "a valid address"
    private var surname: String? = "valid surname"
    private var email: String? = "email@valid.com"
    private var password: String? = "Pass*_word"
    private var cvuMP: String? = "1234567890123456789012"
    private var walletAddress: String? = "12345678"
    private var score: Int = 0
    private var succesfulOperations: Int = 0

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
    fun withScore(score: Int): UserBuilder{
        this.score = score
        return this
    }

    fun withSuccesfulOperations(number: Int): UserBuilder{
        this.succesfulOperations = number
        return this
    }

}
package ar.unq.desapp.grupob.backenddesappapi.model

import ar.unq.desapp.grupob.backenddesappapi.dto.IntentItem
import ar.unq.desapp.grupob.backenddesappapi.utlis.UserCannotBeRegisteredException
import ar.unq.desapp.grupob.backenddesappapi.utlis.UserNotRegisteredException
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class CryptoExchangeSystem {

    private val users: MutableSet<UserEntity> = mutableSetOf()
    private val prices: MutableMap<CryptoCurrency, MutableList<Price>> = mutableMapOf<CryptoCurrency, MutableList<Price>>()
    private val posts: MutableSet<Post> = mutableSetOf()


    fun activePostByUserAndType(userId: Long, operation: OperationType): List<IntentItem> {
        val result = getPostByUser(userId)
            .filter { it.operationType == operation }
            .map { post ->
                val reputation: String = if (post.user!!.operations == 0)  "No operations" else post.user!!.operations.toString()
                IntentItem(
                    post.createdDate!!,
                    post.cryptoCurrency,
                    post.operationType,
                    post.amount,
                    post.price,
                    post.priceInArs,
                    "${post.user!!.name} ${post.user!!.surname}",
                    post.user!!.operations,
                    reputation)
            }
        return result
    }

    fun addPost(post: Post, userId: Long) {
        val userRegistered: UserEntity = getUserById(userId) ?: throw UserNotRegisteredException("The user does not exist")
        post.user = userRegistered
        post.createdDate = LocalDate.now()
        posts.add(post)
    }

    fun getPostByUser(userId: Long): MutableSet<Post>{
        getUserById(userId) ?: throw UserNotRegisteredException("The user does not exist")
        return posts.filter { it.user!!.id == userId}.toMutableSet()
    }

    fun register(user: UserEntity) {
        validUser(user)
        users.add(user)
    }
    fun getPricesFor(cryptoCurrency: CryptoCurrency): MutableList<Price> {
        return prices.getOrDefault(cryptoCurrency, mutableListOf())
    }

    fun registerPrice(cryptoCurrency: CryptoCurrency, price: Price) {
        val pricesForCryptoCurrency = getPricesFor(cryptoCurrency)
        pricesForCryptoCurrency.add(price)
        prices[cryptoCurrency] = pricesForCryptoCurrency
    }
    fun getPricesForTheLast24hs(cryptoCurrency: CryptoCurrency): List<Price> {
        val now = LocalDate.now()
        val minus24hs = now.minus(1, ChronoUnit.DAYS)
        return getPricesFor(cryptoCurrency).filter {
            (it.priceTime.isAfter(minus24hs) || it.priceTime.isEqual(minus24hs)) && (it.priceTime.isBefore(now) || it.priceTime.isEqual(now))
        }
    }

    fun getUserById(id: Long): UserEntity? {
        return users.find {it.id == id}
    }

    private fun validUser(user: UserEntity){
        val emailPattern = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
        val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{6,}\$")
        val cvuPattern = Regex("^\\d{22}$")
        val walletAddressPattern = Regex("^\\d{8}$")

        checkNullAndApply(user.name, UserEntity::name.name, checkForRange(3, 30))
        checkNullAndApply(user.surname, UserEntity::surname.name, checkForRange(3, 30))
        checkNullAndApply(user.email, UserEntity::email.name, checkForPattern(emailPattern, "does not have a valid format"))
        checkNullAndApply(user.address, UserEntity::address.name, checkForRange(10, 30))
        checkNullAndApply(user.password, UserEntity::password.name, checkForPattern(passwordPattern, "is too weak"))
        checkNullAndApply(user.cvu, UserEntity::cvu.name, checkForPattern(cvuPattern, "must have 22 digits"))
        checkNullAndApply(user.walletAddress, UserEntity::walletAddress.name, checkForPattern(walletAddressPattern, "must have 8 digits"))
    }

    private fun checkNullAndApply(value: String?, property: String, function: (value: String, property: String) ->  Unit) {
        if(value == null) throw UserCannotBeRegisteredException("The $property cannot be null")
        function(value, property)
    }

    private val checkForPattern: (pattern: Regex, errorMessage: String) -> (value: String, property: String) -> Unit = {
        pattern, errorMessage -> { value, property ->
            run {
                run {
                    if (!pattern.matches(value)) throw UserCannotBeRegisteredException("The $property $errorMessage")
                }
            }
        }
    }

    private val checkForRange: (min: Int, max: Int) -> (value: String, property: String) -> Unit = {
        min, max -> { value, property ->
            run {
                if(value.length !in min..max) throw UserCannotBeRegisteredException("The $property is too short or too long")
            }
        }
    }

    fun wireTransferNotice(postId: Long, interestedUserId: Long?) {
        getUserById(interestedUserId!!)
        val post = posts.find { it.id == postId }
        posts.remove(post)
        post!!.status = StatusPost.IN_PROGRESS
    }
}

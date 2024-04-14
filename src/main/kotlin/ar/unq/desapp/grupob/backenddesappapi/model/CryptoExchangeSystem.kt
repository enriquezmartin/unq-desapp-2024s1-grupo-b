package ar.unq.desapp.grupob.backenddesappapi.model

import ar.unq.desapp.grupob.backenddesappapi.utlis.UserCannotBeRegisteredException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class CryptoExchangeSystem {

    val users: MutableSet<UserEntity> = mutableSetOf()
    val prices: MutableMap<CryptoCurrency, MutableList<Price>> = mutableMapOf<CryptoCurrency, MutableList<Price>>()

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
}

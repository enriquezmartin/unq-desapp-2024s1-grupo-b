package ar.unq.desapp.grupob.backenddesappapi.model

import ar.unq.desapp.grupob.backenddesappapi.utlis.UserCannotBeRegisteredException

class CryptoExchangeSystem {
    fun register(user: UserEntity) {
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

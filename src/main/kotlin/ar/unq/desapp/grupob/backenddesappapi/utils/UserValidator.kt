package ar.unq.desapp.grupob.backenddesappapi.utils

object UserValidator {

    fun validatePattern(value: String?, pattern: Regex, errorMsg: String): String{
        checkNull(value, errorMsg)
        if (!pattern.matches(value!!)) throw UserCannotBeRegisteredException(errorMsg)
        return value
    }
    fun validateRange(value:String?, min: Int, max: Int, errorMsg: String): String{
        checkNull(value, errorMsg)
        if(value!!.length !in min..max) throw UserCannotBeRegisteredException(errorMsg)
        return value
    }

    private fun checkNull(value: String?, errorMsg: String) {
        if(value == null) throw UserCannotBeRegisteredException(errorMsg)
    }

    val checkForPattern: (pattern: Regex, errorMessage: String) -> (value: String, property: String) -> Unit =
        { pattern, errorMessage ->
            { value, property ->
                if (!pattern.matches(value)) throw UserCannotBeRegisteredException("The $property $errorMessage")
            }
    }

    val checkForRange: (min: Int, max: Int) -> (value: String, property: String) -> Unit =
        { min, max ->
            { value, property ->
                if(value.length !in min..max) throw UserCannotBeRegisteredException("The $property is too short or too long")
            }
        }

}
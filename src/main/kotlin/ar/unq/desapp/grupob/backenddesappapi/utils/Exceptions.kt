package ar.unq.desapp.grupob.backenddesappapi.utils

data class ApiNotResponding(override val message: String?= "API is not responding"): RuntimeException(message)

data class UsernameAlreadyTakenException(override val message: String): RuntimeException(message)
data class UserCannotBeRegisteredException(override val message: String): RuntimeException(message)
data class UserNotRegisteredException(override val message: String): RuntimeException(message)

data class PriceOutOfRangeException(override val message: String? = "The price is out of range"): RuntimeException(message)
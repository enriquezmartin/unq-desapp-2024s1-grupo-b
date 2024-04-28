package ar.unq.desapp.grupob.backenddesappapi.utils

data class UsernameAlreadyTakenException(override val message: String): Exception(message)
data class UserCannotBeRegisteredException(override val message: String): Exception(message)
data class UserNotRegisteredException(override val message: String): Exception(message)
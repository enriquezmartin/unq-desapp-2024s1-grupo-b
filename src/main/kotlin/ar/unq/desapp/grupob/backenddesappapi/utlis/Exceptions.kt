package ar.unq.desapp.grupob.backenddesappapi.utlis

data class UsernameAlreadyTakenException(override val message: String): Exception(message)
data class UserCannotBeRegisteredException(override val message: String): Exception(message)
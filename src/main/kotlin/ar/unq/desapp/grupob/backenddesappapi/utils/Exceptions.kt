package ar.unq.desapp.grupob.backenddesappapi.utils

class ApiNotResponding(): RuntimeException("API is not responding")

data class UsernameAlreadyTakenException(override val message: String): RuntimeException(message)
data class UserCannotBeRegisteredException(override val message: String): RuntimeException(message)
data class UserNotRegisteredException(override val message: String): RuntimeException(message)
class PriceOutOfRangeException(): RuntimeException("The price is out of range")
class UnavailablePostException(): RuntimeException( "The post is not available")
class InvalidUserForPaymentException(): RuntimeException("The client cannot be the same as the post owner")
class InvalidUserOperationException(): RuntimeException("Invalid user to confirm o cancel this operation")
class InvalidOperationException(): RuntimeException("Operation with status closed or cancelled can not be confirmed or canceled.")
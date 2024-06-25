package ar.unq.desapp.grupob.backenddesappapi.utils

class ApiNotResponding(): RuntimeException("API is not responding")

data class UsernameAlreadyTakenException(override val message: String): RuntimeException(message)
data class UserCannotBeRegisteredException(override val message: String): RuntimeException(message)
data class UserNotRegisteredException(override val message: String): RuntimeException(message)
open class ForbiddenException(override val message: String): RuntimeException(message)
class PriceOutOfRangeException(): ForbiddenException("The price is out of range")
class UnavailablePostException(): ForbiddenException( "The post is not available")
class InvalidUserForPaymentException(): ForbiddenException("The client cannot be the same as the post owner")
class InvalidUserOperationException(): ForbiddenException("Invalid user to confirm o cancel this operation")
class InvalidOperationException(): ForbiddenException("Operation with status closed or cancelled can not be confirmed or canceled.")
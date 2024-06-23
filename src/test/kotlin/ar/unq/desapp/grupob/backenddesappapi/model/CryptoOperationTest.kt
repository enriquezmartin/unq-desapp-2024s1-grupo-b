package ar.unq.desapp.grupob.backenddesappapi.model

import ar.unq.desapp.grupob.backenddesappapi.helpers.PostBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.PriceBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.UserBuilder
import ar.unq.desapp.grupob.backenddesappapi.utils.InvalidUserForPaymentException
import ar.unq.desapp.grupob.backenddesappapi.utils.PriceOutOfRangeException
import ar.unq.desapp.grupob.backenddesappapi.utils.UnavailablePostException
import net.bytebuddy.asm.Advice.Local
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private const val OUT_OF_RANGE_MSG_ERROR = "The price is out of range"
private const val INVALID_POST_FOR_PAYMENT_ERROR_MSG = "The post is not available"
private const val INVALID_USER_FOR_PAYMENT_ERROR_MSG = "The client cannot be the same as the post owner"

class CryptoOperationTest {
    @Test
    fun `when an operation is initialized on a purchase post and the value in the latest price is above the price on the post, an exception is raised`(){
        val post: Post = PostBuilder().withOperationType(OperationType.PURCHASE).withPrice(10f).build()
        val price: Price = PriceBuilder().withValue(10.6f).build()
        val user: UserEntity = UserBuilder().build()
        val errorMsg = assertThrows<PriceOutOfRangeException> { CryptoOperation.initOperation(post, price, user) }.message

        assertEquals(errorMsg, OUT_OF_RANGE_MSG_ERROR)
    }
    @Test
    fun `when an operation is initialized on a sale post and the value in the latest price is below the price on the post, an exception is raised`(){
        val post: Post = PostBuilder().withOperationType(OperationType.SALE).withPrice(10f).build()
        val price: Price = PriceBuilder().withValue(9.4f).build()
        val user: UserEntity = UserBuilder().build()
        val errorMsg = assertThrows<PriceOutOfRangeException> { CryptoOperation.initOperation(post, price, user) }.message

        assertEquals(errorMsg, OUT_OF_RANGE_MSG_ERROR)
    }
    @Test
    fun `when an operation is initialized on a closed or in progress post, an exception is raised`(){
        val user = UserBuilder().build()
        val price = PriceBuilder().build()
        val inProgressPost = PostBuilder().withStatus(PostStatus.IN_PROGRESS).build()
        val closedPost = PostBuilder().withStatus(PostStatus.CLOSED).build()

        val inProgressErrorMsg = assertThrows<UnavailablePostException> { CryptoOperation.initOperation(inProgressPost, price, user) }.message
        val closedErrorMsg = assertThrows<UnavailablePostException> { CryptoOperation.initOperation(closedPost, price, user) }.message

        assertEquals(inProgressErrorMsg, INVALID_POST_FOR_PAYMENT_ERROR_MSG)
        assertEquals(closedErrorMsg, INVALID_POST_FOR_PAYMENT_ERROR_MSG)
    }
    @Test
    fun `when an operation is initialized and the client is the same as the post owner, an exception is raised`(){
        val client = UserBuilder().withId(1L).build()
        val owner = UserBuilder().withId(1L).build()
        val post = PostBuilder().withStatus(PostStatus.ACTIVE).withUser(owner).build()
        val price = PriceBuilder().build()

        val errorMsg = assertThrows<InvalidUserForPaymentException> { CryptoOperation.initOperation(post, price, client) }.message

        assertEquals(errorMsg, INVALID_USER_FOR_PAYMENT_ERROR_MSG)
    }
    @Test
    fun `operations created successfully`(){
        val currentTime: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        val client: UserEntity = UserBuilder().withId(1L).build()
        val owner: UserEntity = UserBuilder().withId(2L).build()
        val purchasePost: Post = PostBuilder().withOperationType(OperationType.PURCHASE).withStatus(PostStatus.ACTIVE).withPrice(10f).withUser(owner).build()
        val salePost: Post = PostBuilder().withOperationType(OperationType.SALE).withPrice(10f).withStatus(PostStatus.ACTIVE).withUser(owner).build()

        val priceForPurchasePost: Price = PriceBuilder().withValue(10.5f).build()
        val priceForSalePost: Price = PriceBuilder().withValue(9.5f).build()

        val purchaseOperation: CryptoOperation = CryptoOperation.initOperation(purchasePost, priceForPurchasePost, client)
        val saleOperation: CryptoOperation = CryptoOperation.initOperation(salePost, priceForSalePost, client)

        assertEquals(purchaseOperation.status, OperationStatus.IN_PROGRESS)
        assertEquals(purchaseOperation.dateTime, currentTime)
        assertEquals(saleOperation.status, OperationStatus.IN_PROGRESS)
        assertEquals(saleOperation.dateTime, currentTime)
        assertEquals(purchaseOperation.post!!.status, PostStatus.IN_PROGRESS)
        assertEquals(saleOperation.post!!.status, PostStatus.IN_PROGRESS)
    }


}
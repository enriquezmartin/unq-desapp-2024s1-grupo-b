package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.helpers.OperationBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.PostBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.PriceBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.UserBuilder
import ar.unq.desapp.grupob.backenddesappapi.model.*
import ar.unq.desapp.grupob.backenddesappapi.repository.CryptoOperationRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.PostRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.PriceRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
@ExtendWith(SpringExtension :: class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CryptoOperationServiceTest {

    @Autowired
    private lateinit var service: CryptoOperationService

    @MockBean
    private lateinit var priceRepository: PriceRepository

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var postRepository: PostRepository

    @MockBean
    private lateinit var operationRepository: CryptoOperationRepository


    @Test
    fun payoutNotificationTest(){
        val currentTime: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        val client: UserEntity = UserBuilder().withId(1L).build()
        val owner: UserEntity = UserBuilder().withId(2L).build()
        val post: Post = PostBuilder().withId(1L).withOperationType(OperationType.PURCHASE).withStatus(PostStatus.ACTIVE).withPrice(10f).withUser(owner).build()
        val price: Price = PriceBuilder().withValue(10f).build()

        `when`(postRepository.findById(1L)).thenReturn(Optional.of(post))
        `when`(userRepository.findById(1L)).thenReturn(Optional.of(client))
        `when`(priceRepository.findFirstByCryptoCurrencyOrderByPriceTimeDesc(post.cryptoCurrency!!)).thenReturn(price)

        val operation = service.payoutNotification(post.id!!, client.id!!)

        assertEquals(operation.status, OperationStatus.IN_PROGRESS)
        assertEquals(operation.dateTime, currentTime)
        assertEquals(operation.post!!.status, PostStatus.IN_PROGRESS)
    }

    @Test
    fun confirmTest(){
        var client: UserEntity = UserBuilder().withId(1L).withSuccesfulOperations(0).build()
        var owner: UserEntity = UserBuilder().withId(2L).withScore(0).withSuccesfulOperations(0).build()
        var post = PostBuilder()
            .withStatus(PostStatus.IN_PROGRESS)
            .withUser(owner)
            .build()
        val operation =
            OperationBuilder()
                .withId(1L)
                .withDateTime(LocalDateTime.now())
                .withStatus(OperationStatus.IN_PROGRESS)
                .withPost(post)
                .withClient(client)
                .build()

        `when`(userRepository.findById(1L)).thenReturn(Optional.of(client))
        `when`(userRepository.findById(2L)).thenReturn(Optional.of(owner))
        `when`(operationRepository.findById(1L)).thenReturn(Optional.of(operation))


        val result = service.confirmOperation(owner.id!!, operation.id!!)

        assertEquals(result.post!!.owner!!.score, 10)
    }

    @Test
    fun cancelTest(){
        var client: UserEntity = UserBuilder().withId(1L).withScore(20).build()
        var owner: UserEntity = UserBuilder().withId(2L).build()
        var post = PostBuilder()
            .withStatus(PostStatus.IN_PROGRESS)
            .withUser(owner)
            .build()
        val operation =
            OperationBuilder()
                .withId(1L)
                .withDateTime(LocalDateTime.now())
                .withStatus(OperationStatus.IN_PROGRESS)
                .withPost(post)
                .withClient(client)
                .build()

        `when`(userRepository.findById(1L)).thenReturn(Optional.of(client))
        `when`(userRepository.findById(2L)).thenReturn(Optional.of(owner))
        `when`(operationRepository.findById(1L)).thenReturn(Optional.of(operation))


        val result = service.cancelOperation(client.id!!, operation.id!!)

        assertEquals(result.client!!.score, 0)
    }


}
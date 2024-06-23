package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.helpers.OperationBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.PostBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.PriceBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.UserBuilder
import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.OperationStatus
import ar.unq.desapp.grupob.backenddesappapi.model.OperationType
import ar.unq.desapp.grupob.backenddesappapi.model.PostStatus
import ar.unq.desapp.grupob.backenddesappapi.repository.CryptoOperationRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.PostRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.PriceRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
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
    private lateinit var cryptoOperationRepository: CryptoOperationRepository


    @Test
    fun `Payout notification success`(){
        var client = UserBuilder().withId(1L).build()
        var post = PostBuilder().withId(1L).build()
        `when`(userRepository.findById(client.id!!)).thenReturn(Optional.of(client))
        `when`(postRepository.findById(post.id!!)).thenReturn(Optional.of(post))

        val operation = service.payoutNotification(post.id!!, client.id!!)

        assertEquals(operation.status, OperationStatus.IN_PROGRESS)
        assertEquals(operation.post!!.status, PostStatus.IN_PROGRESS)
        assertEquals(operation.client!!.id, client.id)

    }

    @Test
    fun `when price is out of range for a post in payout notification the post become cancelled`(){
        var client = UserBuilder().withId(1L).build()
        var post = PostBuilder()
            .withId(1L)
            .withOperationType(OperationType.PURCHASE)
            .withCryptoCurrency(CryptoCurrency.AAVEUSDT)
            .withPrice(10F)
            .build()
        var price = PriceBuilder()
            .withValue(15F)
            .build()

        `when`(userRepository.findById(client.id!!)).thenReturn(Optional.of(client))
        `when`(postRepository.findById(post.id!!)).thenReturn(Optional.of(post))
        `when`(priceRepository.findFirstByCryptoCurrencyOrderByPriceTimeDesc(post.cryptoCurrency!!)).thenReturn(price)

        val operation = service.payoutNotification(post.id!!, client.id!!)

        assertEquals(operation.status, OperationStatus.CANCELLED)
        assertEquals(operation.post!!.status, PostStatus.ACTIVE)
    }

    @Test
    fun `confirm`(){
        var owner = UserBuilder()
            .withId(1L)
            .build()
        var client = UserBuilder()
            .build()
        var post = PostBuilder()
            .withStatus(PostStatus.IN_PROGRESS)
            .build()
        var operation = OperationBuilder()
            .withStatus(OperationStatus.IN_PROGRESS)
            .withPost(post)
            .withClient(client)
            .withId(1L)
            .build()

        `when`(cryptoOperationRepository.findById(operation.id!!)).thenReturn(Optional.of(operation))
        `when`(userRepository.findById(owner.id!!)).thenReturn(Optional.of(owner))

        service.confirmOperation(owner.id!!, operation.id!!)

        assertEquals(owner.score,10)
        assertEquals(post.status, PostStatus.CLOSED)
        assertEquals(operation.status, OperationStatus.CLOSED)
        assertEquals(client.score,10)
        assertEquals(owner.succesfulOperation, 1)
        assertEquals(client.succesfulOperation, 1)

    }

}
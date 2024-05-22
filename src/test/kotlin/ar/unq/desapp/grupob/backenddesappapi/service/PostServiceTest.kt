package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.helpers.PostBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.PriceBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.UserBuilder
import ar.unq.desapp.grupob.backenddesappapi.model.*
import ar.unq.desapp.grupob.backenddesappapi.repository.PostRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.PriceRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import ar.unq.desapp.grupob.backenddesappapi.utils.PriceOutOfRangeException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension :: class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostServiceTest {
    @Autowired
    private lateinit var service: PostService

    @MockBean
    private lateinit var priceRepository: PriceRepository

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var postRepository: PostRepository

    @Test
    fun `when user post intent, the price must be in the correct pricing range`(){

        val postWithExceededPrice = PostBuilder().withCryptoCurrency(CryptoCurrency.AAVEUSDT).withPrice(20F).build()
        val postWithFallShortPrice = PostBuilder().withCryptoCurrency(CryptoCurrency.AAVEUSDT).withPrice(4F).build()
        val lastPrice = PriceBuilder()
            .withCryptoCurrency(CryptoCurrency.AAVEUSDT)
            .withValue(10.0F)
            .build()
        val expectedError = "The price is out of range"

        `when`(priceRepository.findFirstByCryptoCurrencyOrderByPriceTimeDesc(CryptoCurrency.AAVEUSDT)).thenReturn(
            lastPrice
        )
        `when`(priceRepository.findFirstByCryptoCurrencyOrderByPriceTimeDesc(CryptoCurrency.AAVEUSDT)).thenReturn(
            lastPrice
        )
        val expectedErrorForExceededPrice = assertThrows<PriceOutOfRangeException> { service.intentPost(
            postWithExceededPrice,
            1L
        ) }.message
        val expectedErrorForFallShortPrice = assertThrows<PriceOutOfRangeException> { service.intentPost(
            postWithFallShortPrice,
            1L
        ) }.message

        assertEquals(expectedErrorForExceededPrice, expectedError)
        assertEquals(expectedErrorForFallShortPrice, expectedError)
    }

    @Test
    fun `when user post intent, a relation between post an user is created`(){
        val user = UserBuilder().withId(1L).build()
        val post = PostBuilder().withId(null).withPrice(20F).build()
        val price = PriceBuilder().withValue(20F).build()

        `when`(priceRepository.findFirstByCryptoCurrencyOrderByPriceTimeDesc(post.cryptoCurrency!!)).thenReturn(
            price
        )
        `when`(userRepository.findById(user.id!!)).thenReturn(
            Optional.of(user)
        )

        val savedPost: Post = service.intentPost(post, user.id!!)
        assertEquals(post.owner!!.id!!, user.id!!)
        //assertTrue(savedPost.id!! != null)
    }

    @Test
    fun `active posts are retrieved by its status`(){
        service.getActivePost()
        verify(postRepository).findByStatus(PostStatus.ACTIVE)
    }


}
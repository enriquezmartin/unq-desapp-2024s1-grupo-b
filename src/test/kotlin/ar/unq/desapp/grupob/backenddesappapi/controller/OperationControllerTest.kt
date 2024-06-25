package ar.unq.desapp.grupob.backenddesappapi.controller

import ar.unq.desapp.grupob.backenddesappapi.helpers.OperationBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.PostBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.PriceBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.UserBuilder
import ar.unq.desapp.grupob.backenddesappapi.model.*
import ar.unq.desapp.grupob.backenddesappapi.service.CryptoOperationService
import ar.unq.desapp.grupob.backenddesappapi.service.PriceService
import ar.unq.desapp.grupob.backenddesappapi.service.UserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class OperationControllerTest {

    @MockBean
    lateinit var operationService: CryptoOperationService

    @MockBean
    lateinit var userService: UserService

    @MockBean
    lateinit var priceService: PriceService

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Test
    @WithMockUser
    fun testNotify(){
        val userId = 1L
        val postId = 1L

        val owner: UserEntity = UserBuilder()
            .withWalletAddress("12345678")
            .withId(userId)
            .withSurname("surname")
            .withName("name")
            .build()
        val client: UserEntity = UserBuilder()
            .withId(2L)
            .withWalletAddress("12345678")
            .withId(userId)
            .withSurname("surname")
            .withName("name")
            .build()
        val price: Price = PriceBuilder()
            .withId(1L)
            .withCryptoCurrency(CryptoCurrency.AAVEUSDT)
            .withValue(100f)
            .build()
        val post: Post = PostBuilder()
            .withOperationType(OperationType.PURCHASE)
            .withCryptoCurrency(CryptoCurrency.AAVEUSDT)
            .withAmount(100f)
            .withUser(client)
            .withPrice(100f)
            .build()
        val operation: CryptoOperation = OperationBuilder()
            .withClient(client)
            .withPost(post)
            .build()

        `when`(userService.findUserBYId(userId)).thenReturn(owner)
        `when`(priceService.getLastPrice(price.cryptoCurrency!!)).thenReturn(price)
        `when`(operationService.payoutNotification(postId, userId)).thenReturn(operation)


        mockMvc.perform(
            post("/operation/notify_payout/$userId/$postId")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.cryptoActive").value("AAVEUSDT"))
            .andExpect(jsonPath("$.nominalQuantity").value(100.0))
            .andExpect(jsonPath("$.currencyPrice").value(100.0))
            .andExpect(jsonPath("$.amount").value(100.0))
            .andExpect(jsonPath("$.user").value("name surname"))
            .andExpect(jsonPath("$.numberOfOperations").value(0))
            .andExpect(jsonPath("$.reputation").value(0))
            .andExpect(jsonPath("$.shippingAddress").value(owner.walletAddress))
            .andReturn()

    }

    @Test
    @WithMockUser
    fun testConfirm(){
        val userId = 1L
        val postId = 1L

        val owner: UserEntity = UserBuilder()
            .withWalletAddress("12345678")
            .withId(userId)
            .withSurname("surname")
            .withName("name")
            .build()
        val client: UserEntity = UserBuilder()
            .withId(2L)
            .withWalletAddress("12345678")
            .withId(userId)
            .withSurname("surname")
            .withName("name")
            .build()
        val price: Price = PriceBuilder()
            .withId(1L)
            .withCryptoCurrency(CryptoCurrency.AAVEUSDT)
            .withValue(100f)
            .build()
        val post: Post = PostBuilder()
            .withOperationType(OperationType.PURCHASE)
            .withCryptoCurrency(CryptoCurrency.AAVEUSDT)
            .withAmount(100f)
            .withUser(client)
            .withPrice(100f)
            .build()
        val operation: CryptoOperation = OperationBuilder()
            .withId(1L)
            .withClient(client)
            .withPost(post)
            .build()

        `when`(userService.findUserBYId(userId)).thenReturn(owner)
        `when`(priceService.getLastPrice(price.cryptoCurrency!!)).thenReturn(price)
        `when`(operationService.cancelOperation(operation.id!!, userId)).thenReturn(operation)


        mockMvc.perform(
            post("/operation/confirm/$userId/$postId")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.cryptoActive").value("AAVEUSDT"))
            .andExpect(jsonPath("$.nominalQuantity").value(100.0))
            .andExpect(jsonPath("$.currencyPrice").value(100.0))
            .andExpect(jsonPath("$.amount").value(100.0))
            .andExpect(jsonPath("$.user").value("name surname"))
            .andExpect(jsonPath("$.numberOfOperations").value(0))
            .andExpect(jsonPath("$.reputation").value(0))
            .andExpect(jsonPath("$.shippingAddress").value(null))
            .andReturn()
    }

    @Test
    @WithMockUser
    fun testCancel(){
        val userId = 1L
        val postId = 1L

        val owner: UserEntity = UserBuilder()
            .withWalletAddress("12345678")
            .withId(userId)
            .withSurname("surname")
            .withName("name")
            .build()
        val client: UserEntity = UserBuilder()
            .withId(2L)
            .withWalletAddress("12345678")
            .withId(userId)
            .withSurname("surname")
            .withName("name")
            .build()
        val price: Price = PriceBuilder()
            .withId(1L)
            .withCryptoCurrency(CryptoCurrency.AAVEUSDT)
            .withValue(100f)
            .build()
        val post: Post = PostBuilder()
            .withOperationType(OperationType.PURCHASE)
            .withCryptoCurrency(CryptoCurrency.AAVEUSDT)
            .withAmount(100f)
            .withUser(client)
            .withPrice(100f)
            .build()
        val operation: CryptoOperation = OperationBuilder()
            .withId(1L)
            .withClient(client)
            .withPost(post)
            .build()

        `when`(userService.findUserBYId(userId)).thenReturn(owner)
        `when`(priceService.getLastPrice(price.cryptoCurrency!!)).thenReturn(price)
        `when`(operationService.cancelOperation(operation.id!!, userId)).thenReturn(operation)


        mockMvc.perform(
            post("/operation/cancel/$userId/$postId")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.cryptoActive").value("AAVEUSDT"))
            .andExpect(jsonPath("$.nominalQuantity").value(100.0))
            .andExpect(jsonPath("$.currencyPrice").value(100.0))
            .andExpect(jsonPath("$.amount").value(100.0))
            .andExpect(jsonPath("$.user").value("name surname"))
            .andExpect(jsonPath("$.numberOfOperations").value(0))
            .andExpect(jsonPath("$.reputation").value(0))
            .andExpect(jsonPath("$.shippingAddress").value(null))
            .andReturn()
    }
}
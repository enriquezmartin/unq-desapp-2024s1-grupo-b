package ar.unq.desapp.grupob.backenddesappapi.controller

import ar.unq.desapp.grupob.backenddesappapi.dtos.PostDTO
import ar.unq.desapp.grupob.backenddesappapi.helpers.MockitoHelper
import ar.unq.desapp.grupob.backenddesappapi.helpers.PostBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.UserBuilder
import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.OperationType
import ar.unq.desapp.grupob.backenddesappapi.model.Post
import ar.unq.desapp.grupob.backenddesappapi.service.PostService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.binance.BinanceApiService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.dolarApi.DolarApiService
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.dolarApi.DolarPriceResponse
import ar.unq.desapp.grupob.backenddesappapi.utils.Mapper
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var postService: PostService

    @MockBean
    private lateinit var dollarApiService: DolarApiService

    @MockBean
    private lateinit var binanceApiService: BinanceApiService

    @Test
    @WithMockUser
    fun `test postIntent endpoint`() {
    //crear el owner!
        val owner = UserBuilder()
            .withId(1L)
            .build()
        val currentDate = LocalDateTime.now()
        val formattedDate = currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val userId = owner.id!!
        val post: Post = PostBuilder()
            .withId(12L)
            .withUser(owner)
            .withCryptoCurrency(CryptoCurrency.ALICEUSDT)
            .withOperationType(OperationType.PURCHASE)
            .withPrice(100.0f)
            .withAmount(1f)
            .withCreatedDate(currentDate)
            .build()
        val postDTO = PostDTO(
            price = 100.0f,
            amount = 1f,
            cryptoCurrency = CryptoCurrency.ALICEUSDT.name,
            operation = OperationType.PURCHASE.name
        )

        val mockResponse = DolarPriceResponse(
            "USD","cripto","Cripto","1078", "1095", formattedDate
        )

        `when`(postService.intentPost(MockitoHelper.anyObject(), eq(userId))).thenReturn(post)
        `when`(dollarApiService.getDolarCryptoPrice()).thenReturn(mockResponse)


        mockMvc.perform(
            post("/post/$userId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(postDTO))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.cryptoCurrency").value(post.cryptoCurrency.toString()))
            .andExpect(jsonPath("$.amount").value(post.amount))
            .andExpect(jsonPath("$.price").value(post.price))
            .andExpect(jsonPath("$.operation").value(post.operationType.toString()))
    }

    @Test
    @WithMockUser
    fun `test getActivePosts endpoint`() {
        val owner = UserBuilder()
            .withId(1L)
            .build()
        val currentDate = LocalDateTime.now()
        val formattedDate = currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val userId = owner.id!!
        val post: Post = PostBuilder()
            .withId(12L)
            .withUser(owner)
            .withCryptoCurrency(CryptoCurrency.ALICEUSDT)
            .withOperationType(OperationType.PURCHASE)
            .withPrice(100.0f)
            .withAmount(1f)
            .withCreatedDate(currentDate)
            .build()
        val posts = listOf(post)

        val mockResponse = DolarPriceResponse(
            "USD","cripto","Cripto","1078", "1095", formattedDate
        )

        `when`(postService.getActivePost()).thenReturn(posts)
        `when`(dollarApiService.getDolarCryptoPrice()).thenReturn(mockResponse)

        mockMvc.perform(get("/activePosts"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].cryptoCurrency").value(post.cryptoCurrency.toString()))
            .andExpect(jsonPath("$[0].amount").value(post.amount))
            .andExpect(jsonPath("$[0].price").value(post.price))
            .andExpect(jsonPath("$[0].operation").value(post.operationType.toString()))
    }
}

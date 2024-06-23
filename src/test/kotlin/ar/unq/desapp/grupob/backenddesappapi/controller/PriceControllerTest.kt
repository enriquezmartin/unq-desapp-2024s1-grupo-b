package ar.unq.desapp.grupob.backenddesappapi.controller

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.Price
import ar.unq.desapp.grupob.backenddesappapi.service.PriceService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var priceService: PriceService

    @Test
    @WithMockUser // Simula un usuario autenticado
    fun `test getPrices endpoint`() {
        `when`(priceService.getAllPrices())
            .thenReturn(listOf(Price(CryptoCurrency.ALICEUSDT, 10.0f)))

        mockMvc.perform(get("/getPrices"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].cryptoCurrency").value("ALICEUSDT"))
            .andExpect(jsonPath("$[0].value").value(10.0))
    }

    @Test
    @WithMockUser
    fun `test getPrices by crypto endpoint`() {
        `when`(priceService.getPrices(CryptoCurrency.ALICEUSDT)).thenReturn(listOf(Price(CryptoCurrency.ALICEUSDT, 10.0f)))

        mockMvc.perform(get("/getPrices/ALICEUSDT"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].cryptoCurrency").value("ALICEUSDT"))
            .andExpect(jsonPath("$[0].value").value(10.0))
    }

    @Test
    @WithMockUser
    fun `test getLatestPrices by crypto endpoint`() {
        `when`(priceService.getLatestPrices(CryptoCurrency.ALICEUSDT))
            .thenReturn(listOf(Price(CryptoCurrency.ALICEUSDT, 10.0f)))

        mockMvc.perform(get("/getLatestPrices/ALICEUSDT"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].cryptoCurrency").value("ALICEUSDT"))
            .andExpect(jsonPath("$[0].value").value(10.0))
    }

//    @Test
//    @WithMockUser
//    fun `test updatePrices endpoint`() {
//        doNothing().`when`(priceService).updatePrices()
//
//        mockMvc.perform(post("/updatePrices"))
//            .andExpect(status().isOk)
//            .andExpect(content().string("Prices updated successfully"))
//    }
}

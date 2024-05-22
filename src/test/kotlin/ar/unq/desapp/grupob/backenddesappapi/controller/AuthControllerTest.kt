package ar.unq.desapp.grupob.backenddesappapi.controller

import ar.unq.desapp.grupob.backenddesappapi.dtos.LoginDTO
import ar.unq.desapp.grupob.backenddesappapi.dtos.RegisterDTO
import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import ar.unq.desapp.grupob.backenddesappapi.service.AuthService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var authService: AuthService

    @Test
    fun `test register endpoint`() {
        val registerDto = RegisterDTO(
            email = "avalid@email.com",
            password = "P4SS+_W0rd",
            name = "Lalo",
            surname = "Landa",
            address = "Calle falsa 123",
            cvuMp = "1234567890123456789012",
            walletAddress = "19283746"
        )

        val user = UserEntity(
            registerDto.email,
            registerDto.password,
            registerDto.name,
            registerDto.surname,
            registerDto.address,
            registerDto.cvuMp,
            registerDto.walletAddress
        )

        `when`(authService.register(user)).thenReturn("User registered successfully")

        mockMvc.perform(
            post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(registerDto))
        )
            .andExpect(status().isOk)
         //   .andExpect(content().string("User registered successfully"))
        //No puedo assertar el contenido, porque es el token de seguridad...
    }

    @Test
    fun `test login endpoint`() {
        val loginDto = LoginDTO(
            email = "some_valid@email.com",
            password = "P4SS+_W0rd"
        )

        `when`(authService.login(loginDto)).thenReturn("Login successful")

        mockMvc.perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(loginDto))
        )
            .andExpect(status().isOk)
            //No puedo assertar el contenido, porque es el token de seguridad...
    }
}

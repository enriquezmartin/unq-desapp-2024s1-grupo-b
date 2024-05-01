package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.OperationType
import ar.unq.desapp.grupob.backenddesappapi.model.Post
import ar.unq.desapp.grupob.backenddesappapi.model.Price
import ar.unq.desapp.grupob.backenddesappapi.repository.PostRepository
import ar.unq.desapp.grupob.backenddesappapi.repository.PriceRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension :: class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostServiceTest {
    @Autowired
    private lateinit var service: PostService

    @MockBean
    private lateinit var priceRepository: PriceRepository

    @Test
    fun `when user post purchase intent, the price must be in the correct pricing range`(){
        /*  postear intencion de compra
            traer la ultima cotizacion del crypto
            validar que la misma este en el rango +/- 5% del precio publicado
         */
        val post = Post(CryptoCurrency.AAVEUSDT,1F,4F,OperationType.PURCHASE)

        `when`(priceRepository.findByCryptoCurrency(CryptoCurrency.AAVEUSDT)).thenReturn(listOf(
            Price(CryptoCurrency.AAVEUSDT,10.0F)
        ))

        assertThrows<Exception> { service }
    }
}
package ar.unq.desapp.grupob.backenddesappapi.repository

import ar.unq.desapp.grupob.backenddesappapi.helpers.OperationBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.PostBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.UserBuilder
import ar.unq.desapp.grupob.backenddesappapi.model.CryptoCurrency
import ar.unq.desapp.grupob.backenddesappapi.model.OperationStatus
import ar.unq.desapp.grupob.backenddesappapi.model.PostStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.*


@SpringBootTest
class CryptoOperationRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var postRepository: PostRepository
    @Autowired
    lateinit var operationRepository: CryptoOperationRepository
    @AfterEach
    fun clean() {
        operationRepository.deleteAll()
        postRepository.deleteAll()
        userRepository.deleteAll()
        //dataService.cleanUp()
    }

    @Test
    fun `Operation for report`(){
        var user = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid surname")
            .withEmail("valid@asd.com")
            .withAddress("a valid address")
            .withPassword("Pass*Word")
            .withCvuMP("1234567890123456789012")
            .withWalletAddress("12345678")
            .build()
        var anotherUser = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid surname")
            .withEmail("anothervalid@asd.com")
            .withAddress("a valid address")
            .withPassword("Pass*Word")
            .withCvuMP("2234567890123456789012")
            .withWalletAddress("22345678")
            .build()
        userRepository.saveAll(listOf(user, anotherUser))
        var post = PostBuilder().withStatus(PostStatus.IN_PROGRESS)
            .withCryptoCurrency(CryptoCurrency.AAVEUSDT)
            .withUser(user)
            .withPrice(100f)
            .withAmount(1f)
            .build()
        var postForNotInRange = PostBuilder().withStatus(PostStatus.IN_PROGRESS)
            .withCryptoCurrency(CryptoCurrency.AAVEUSDT)
            .withUser(user)
            .withPrice(100f)
            .withAmount(1f)
            .build()
        var postForNotClosed = PostBuilder().withStatus(PostStatus.IN_PROGRESS)
            .withCryptoCurrency(CryptoCurrency.AAVEUSDT)
            .withUser(user)
            .withPrice(100f)
            .withAmount(1f)
            .build()
        var postForAsClient = PostBuilder().withStatus(PostStatus.IN_PROGRESS)
            .withCryptoCurrency(CryptoCurrency.AAVEUSDT)
            .withUser(anotherUser)
            .withPrice(100f)
            .withAmount(1f)
            .build()

        postRepository.saveAll(listOf(post, postForNotInRange, postForNotClosed, postForAsClient))

        var operationAsOwner = OperationBuilder()
            .withDateTime(LocalDateTime.now().minusDays(1))
            .withPost(post)
            .withClient(anotherUser)
            .withStatus(OperationStatus.CLOSED)
            .build()
        var operationAsClient = OperationBuilder()
            .withDateTime(LocalDateTime.now().minusDays(1))
            .withPost(postForAsClient)
            .withClient(user)
            .withStatus(OperationStatus.CLOSED)
            .build()
        var notInRangeOperation = OperationBuilder()
            .withDateTime(LocalDateTime.now().minusDays(3))
            .withPost(postForNotInRange)
            .withClient(anotherUser)
            .withStatus(OperationStatus.IN_PROGRESS)
            .build()
        var notClosedOperation = OperationBuilder()
            .withDateTime(LocalDateTime.now().minusDays(3))
            .withPost(postForNotClosed)
            .withClient(anotherUser)
            .withStatus(OperationStatus.IN_PROGRESS)
            .build()

        operationRepository.saveAll(listOf(operationAsOwner, notInRangeOperation, notClosedOperation, operationAsClient))

        val startDate = LocalDateTime.now().minusDays(2)
        val endDate = LocalDateTime.now()
        val result = operationRepository.getByOwnerBetweenDates( user.id!!, startDate, endDate)

        val operationIsPresent = result.any{ it.id == operationAsOwner.id }
        val notInRangeOperationIsPresent = result.any{ it.id == notInRangeOperation.id }
        val notClosedIsPresent = result.any { it.id == notClosedOperation.id }
        val operationAsClientIsPresent = result.any { it.id == operationAsClient.id }

        assertTrue(operationIsPresent)
        assertTrue(operationAsClientIsPresent)
        assertFalse(notInRangeOperationIsPresent)
        assertFalse(notClosedIsPresent)

    }
}
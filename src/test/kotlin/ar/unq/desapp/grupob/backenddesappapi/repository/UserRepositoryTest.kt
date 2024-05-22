package ar.unq.desapp.grupob.backenddesappapi.repository

import ar.unq.desapp.grupob.backenddesappapi.helpers.UserBuilder
import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import jakarta.transaction.Transactional
import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.junit.jupiter.SpringExtension

//@ExtendWith(SpringExtension :: class)
@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    lateinit var userDao: UserRepository

    @AfterEach
    fun clean() {
        userDao.deleteAll()
        //dataService.cleanUp()
    }

    var validUser = UserBuilder()
        .withId(1L)
        .withName("a valid name")
        .withSurname("a valid surname")
        .withEmail("valid@asd.com")
        .withAddress("a valid address")
        .withPassword("Pass*Word")
        .withCvuMP("1234567890123456789012")
        .withWalletAddress("12345678")
        .build()

    @Test
    fun `when two users with the same email are saved, an exception is raised`(){
        val userWithAlreadyTakenEmail = UserBuilder()
            .withId(2L)
            .withName("another valid name")
            .withSurname("another valid surname")
            .withEmail("valid@asd.com")
            .withAddress("another valid address")
            .withPassword("aPass*Word")
            .withCvuMP("1234567890123456789012")
            .withWalletAddress("12345678")
            .build()

        assertThrows<DataIntegrityViolationException> {
            userDao.save(validUser)
            userDao.save(userWithAlreadyTakenEmail)
        }
    }

    @Test
    fun `when two users with the same wallet address are saved, an exception is raised`(){
        val userWithAlreadyTakenWalletAddress = UserBuilder()
            .withId(2L)
            .withName("another valid name")
            .withSurname("another valid surname")
            .withEmail("anotherValid@email.com")
            .withAddress("another valid address")
            .withPassword("aPass*Word")
            .withCvuMP("1234567890123456789012")
            .withWalletAddress("12345678")
            .build()

        assertThrows<DataIntegrityViolationException> {
            userDao.save(validUser)
            userDao.save(userWithAlreadyTakenWalletAddress)
        }
    }
}
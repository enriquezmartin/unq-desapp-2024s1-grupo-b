package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.dtos.LoginDTO
import ar.unq.desapp.grupob.backenddesappapi.helpers.UserBuilder
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import ar.unq.desapp.grupob.backenddesappapi.utils.UsernameAlreadyTakenException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension :: class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var authService: AuthService

    @MockBean
    lateinit var userDao: UserRepository

    // Extensión para convertir Optional a T?
    fun <T> Optional<T>.toNullable(): T? = orElse(null)

    @Test
    fun `test register valid user does not throw any exceptions`(){
        val user = UserBuilder()
            .withId(1L)
            .withEmail("email@valid.com")
            .withPassword("@V@lidPassw0rd")
            .build()
        `when`(userDao.save(user)).thenReturn(user)
        assertDoesNotThrow{
            authService.register(user)
        }
    }

    @Test
    fun `when DataIntegrityViolationException is raised in a register method an UsernameAlreadyTaken exception is raised`(){
        val user = UserBuilder()
            .withEmail("email@valid.com")
            .build()
       `when`(userDao.save(user)).thenThrow(DataIntegrityViolationException(""))
       val exception = assertThrows<UsernameAlreadyTakenException>{
           authService.register(user)
       }
       assertEquals("User with username email@valid.com has already been taken", exception.message)
    }

    @Test
    fun `test find user by id when valid`(){
        val user = UserBuilder()
            .withId(2L)
            .build()
        `when`(userDao.findById(user.id!!)).thenReturn(Optional.of(user))
        val foundUser = userService.findUserById(user.id!!)
        assertEquals(user.id, foundUser.id)
    }

    @Test
    fun `test find user by email when valid`(){
        val user = UserBuilder()
            .withEmail("email@valid.com")
            .build()
        `when`(userDao.findByEmail(user.email!!)).thenReturn(Optional.of(user).toNullable())
        val foundUser = userService.loadUserByUsername("email@valid.com")
        assertEquals(user.email, foundUser.username)
    }

    @Test
    fun `test find user by email when invalid throws an exception`(){
        val user = UserBuilder()
            .withEmail("email@valid.com")
            .build()
        `when`(userDao.findByEmail(user.email!!)).thenReturn(Optional.of(user).toNullable())

        val exception = assertThrows<UsernameNotFoundException>{
            userService.loadUserByUsername("email@not_valid.com")
        }
        assertEquals("User with email email@not_valid.com does not exist.", exception.message)
    }

    @Test
    fun `test login user when invalid throws an exception`(){
        val badlogin = LoginDTO(
            "email@not_valid.com",
            "@doesNotExist"
        )
        val exception = assertThrows<BadCredentialsException>{
            authService.login(badlogin)
        }
        assertEquals("Bad credentials", exception.message)
    }

    @Test
    fun `test login user when valid`(){
        val user = UserBuilder()
            .withId(1L)
            .withEmail("email@valid.com")
            .withPassword("@V@lidPassw0rd")
            .build()
        `when`(userDao.save(user)).thenReturn(user)
        `when`(userDao.findByEmail(user.email!!)).thenReturn(user)

        val validLogin = LoginDTO(
            user.email!!,
            user.password!!
        )
        authService.register(user)
        assertDoesNotThrow { authService.login(validLogin) }
    }




}
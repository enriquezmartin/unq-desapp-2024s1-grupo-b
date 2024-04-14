package ar.unq.desapp.grupob.backenddesappapi.repository

import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import jakarta.validation.ConstraintViolationException
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

//    var user1: UserEntity = UserEntity("username","password")
//    var user2: UserEntity = UserEntity("username","password")
//
//    @Test()
//    fun `when two users with the same username are saved, an exception is raised`(){
//        assertThrows<DataIntegrityViolationException> {
//            user1 = userDao.save(user1)
//            user2 = userDao.save(user2)
//        }
//    }
}
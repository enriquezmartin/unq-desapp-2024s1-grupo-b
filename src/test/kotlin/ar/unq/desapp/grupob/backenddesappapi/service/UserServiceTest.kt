package ar.unq.desapp.grupob.backenddesappapi.service

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension :: class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

//    @Autowired
//    lateinit var service: UserService
//
//    @MockBean
//    lateinit var userDao: UserRepository
//
//    //var user: UserEntity = UserEntity("username","password")
//
//    @Test
//    fun `when DataIntegrityViolationException is raised in a register method an UsernameAlreadyTaken exception is raised`(){
//       `when`(userDao.save(user)).thenThrow(DataIntegrityViolationException(""))
//       val exception = assertThrows<UsernameAlreadyTakenException>{
//           service.register(user)
//       }
//       assertEquals(exception.message, "Username username has already taken")
//    }
}
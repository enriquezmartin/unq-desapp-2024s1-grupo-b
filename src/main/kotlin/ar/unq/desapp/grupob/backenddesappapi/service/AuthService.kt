package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.dtos.LoginDTO
import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import ar.unq.desapp.grupob.backenddesappapi.thirdApiService.PriceAutoUpdater
import ar.unq.desapp.grupob.backenddesappapi.utils.UsernameAlreadyTakenException
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class AuthService {

    private val logger = LoggerFactory.getLogger(AuthService::class.java)
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    lateinit var jwtService: JwtService
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    fun register(user: UserEntity): String {
        try{
            user.password = passwordEncoder.encode(user.password)
            userRepository.save(user)
            //println(user.password)
            logger.info("User with email: ${user.email} created")
            return jwtService.generateToken(user)
        }
        catch (e: DataIntegrityViolationException){
            throw UsernameAlreadyTakenException("User with username ${user.email} has already been taken")
        }

    }

    fun registerAll(users: List<UserEntity>) {
        users.forEach{ user ->
            user.password = passwordEncoder.encode(user.password)
            userRepository.save(user)
        }
    }

    fun login(loginDTO: LoginDTO): String {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDTO.email,
                loginDTO.password
            )
        )
        val user: UserEntity? = userRepository.findByEmail(loginDTO.email)
        logger.info("Login: ${user!!.email}")
        return jwtService.generateToken(user)
    }
}
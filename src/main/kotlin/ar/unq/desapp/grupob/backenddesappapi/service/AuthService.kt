package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.dtos.LoginDTO
import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class AuthService {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    lateinit var jwtService: JwtService
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    fun register(user: UserEntity): String {
        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)
        println(user.password)
        return jwtService.generateToken(user)
    }

    fun login(loginDTO: LoginDTO): String {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginDTO.email,
                loginDTO.password
            )
        )
        val user: Optional<UserEntity> = userRepository.findByEmail(loginDTO.email)
        println("login: ${user.get().email}")
        return jwtService.generateToken(user.get())
    }
}
package ar.unq.desapp.grupob.backenddesappapi.controller

import ar.unq.desapp.grupob.backenddesappapi.dtos.LoginDTO
import ar.unq.desapp.grupob.backenddesappapi.dtos.RegisterDTO
import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import ar.unq.desapp.grupob.backenddesappapi.service.AuthService
import ar.unq.desapp.grupob.backenddesappapi.utils.MetricsRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @Autowired
    private lateinit var metricsRegistry: MetricsRegistry

    @Autowired
    lateinit var authService: AuthService
    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterDTO): ResponseEntity<String> {
        val user = UserEntity(registerDto.email, registerDto.password, registerDto.name, registerDto.surname, registerDto.address, registerDto.cvuMp, registerDto.walletAddress)
        val result = authService.register(user)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ResponseEntity<String> {
        metricsRegistry.loginAttemptsCounter.increment() //Counts login attempts.
        val result = authService.login(loginDTO)
        return ResponseEntity.ok(result)
    }
}
package ar.unq.desapp.grupob.backenddesappapi.controller

import ar.unq.desapp.grupob.backenddesappapi.dtos.LoginDTO
import ar.unq.desapp.grupob.backenddesappapi.dtos.RegisterDTO
import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import ar.unq.desapp.grupob.backenddesappapi.service.AuthService
import ar.unq.desapp.grupob.backenddesappapi.utils.MetricsRegistry
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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

    @Operation(
        summary = "User registration",
        description = "Creates new user with provided data and returns JWT token if successful."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Registration successful."),
            ApiResponse(responseCode = "400", description = "Bad request. Check registration form parameters."),
        ]
    )
    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterDTO): ResponseEntity<String> {
        val user = UserEntity(registerDto.email, registerDto.password, registerDto.name, registerDto.surname, registerDto.address, registerDto.cvuMp, registerDto.walletAddress)
        val result = authService.register(user)
        return ResponseEntity.ok(result)
    }

    @Operation(
        summary = "User Login",
        description = "Authenticates user and returns JWT token if successful."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Login successful."),
            ApiResponse(responseCode = "400", description = "Bad request. Check login parameters."),
            ApiResponse(responseCode = "401", description = "User does not exist."),
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ResponseEntity<String> {
        metricsRegistry.loginAttemptsCounter.increment() //Counts login attempts.
        val result = authService.login(loginDTO)
        return ResponseEntity.ok(result)
    }
}
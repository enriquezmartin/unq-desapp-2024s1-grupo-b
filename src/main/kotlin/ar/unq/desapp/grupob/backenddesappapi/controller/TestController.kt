package ar.unq.desapp.grupob.backenddesappapi.controller


import ar.unq.desapp.grupob.backenddesappapi.dtos.LoginDTO
import ar.unq.desapp.grupob.backenddesappapi.dtos.RegisterDTO
import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import ar.unq.desapp.grupob.backenddesappapi.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @Autowired
    lateinit var authService: AuthService

    @GetMapping("/admin/test2")
    fun admin(): String {
        return "Hola"
    }

    @GetMapping("/test2")
    fun user(): String {
        return "Hola no admin"
    }

    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterDTO):String{
        var user = UserEntity(registerDto.email, registerDto.password, registerDto.name, registerDto.surname, registerDto.address, registerDto.cvuMp, registerDto.walletAddress)
        return authService.register(user)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO):String{
        return authService.login(loginDTO)
    }
}
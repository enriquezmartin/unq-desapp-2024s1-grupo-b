package ar.unq.desapp.grupob.backenddesappapi.controller


import ar.unq.desapp.grupob.backenddesappapi.DTO.Mapper
import ar.unq.desapp.grupob.backenddesappapi.DTO.RegisterDTO
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import ar.unq.desapp.grupob.backenddesappapi.security.jwt.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class AuthController {

    @Autowired
    lateinit var userDao: UserRepository

    @Autowired
    lateinit var mapper: Mapper

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @GetMapping("/test")
    fun test():String{
        return "success"
    }

    @PostMapping("/register")
    fun register(@RequestBody userDTO: RegisterDTO): String{
        try {
            var user = mapper.fromRegisterDTOtoUser(userDTO)
            var token = jwtUtils.generateAccessToken(user.username!!)
            userDao.save(user)
            return token
        } catch (e: Exception){ return "fail ${e.message} "}

    }
}
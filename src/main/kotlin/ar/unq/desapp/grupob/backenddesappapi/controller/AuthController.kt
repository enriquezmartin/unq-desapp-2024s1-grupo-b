package ar.unq.desapp.grupob.backenddesappapi.controller


import ar.unq.desapp.grupob.backenddesappapi.dto.Mapper
import ar.unq.desapp.grupob.backenddesappapi.dto.RegisterDTO
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import ar.unq.desapp.grupob.backenddesappapi.security.jwt.JwtUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @Autowired
    lateinit var userDao: UserRepository

    @Autowired
    lateinit var mapper: Mapper

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @Operation(summary = "Endpoint Get Test", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202", description = "Successful"),
            ApiResponse(responseCode = "404", description = "Failed"),
        ]
    )

    @GetMapping("/test")
    fun test():String{
        return "Successful"
    }

    @PostMapping("/register")
    fun register(@RequestBody userDTO: RegisterDTO): String{
        try {
            var user = mapper.fromRegisterDTOtoUser(userDTO)
            var token = jwtUtils.generateAccessToken(user.username!!)
            userDao.save(user)
            return token
        } catch (e: Exception){ return "Failed: ${e.message} "}

    }
}
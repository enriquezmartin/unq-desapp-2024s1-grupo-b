package ar.unq.desapp.grupob.backenddesappapi.dto

import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class Mapper {


    @Autowired
    lateinit var encoder: PasswordEncoder

    fun fromRegisterDTOtoUser(dto: RegisterDTO): UserEntity{
        val user = UserEntity()
        user.username = dto.username
        user.password = encoder.encode(dto.password)
        return user
    }
}
package ar.unq.desapp.grupob.backenddesappapi.service

import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService: UserDetailsService {
    fun findUserByEmail(email: String): UserEntity?
    fun findUserById(id: Long): UserEntity
}
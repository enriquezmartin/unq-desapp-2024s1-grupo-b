package ar.unq.desapp.grupob.backenddesappapi.service.impl

import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import ar.unq.desapp.grupob.backenddesappapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

fun <T> Optional<T>.toNullable(): T? = orElse(null)

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userDao: UserRepository
    override fun findUserByEmail(email: String): UserEntity? {
        return userDao.findByEmail(email)
    }

    fun findById(id: Long): UserEntity? {
        return userDao.findById(id).toNullable()
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        var userdetails: UserEntity = userDao.findByEmail(username!!)
            ?: throw UsernameNotFoundException("El usuario no existe")

        //println("user: ${userdetails.email}")
        var authorities: Set<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_USER") ).toSet()
        return User(userdetails.email, userdetails.password, true, true, true, true, authorities)
    }
}
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

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userDao: UserRepository
    override fun findUserByEmail(email: String): UserEntity? {
        return userDao.findByEmail(email)
    }

    override fun findUserBYId(id: Long): UserEntity {
        return userDao.findById(id).get()
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val userdetails: UserEntity = userDao.findByEmail(username!!)
            ?: throw UsernameNotFoundException("El usuario no existe")

        val authorities: Set<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_USER") ).toSet()
        return User(userdetails.email, userdetails.password, true, true, true, true, authorities)
    }
}
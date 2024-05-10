package ar.unq.desapp.grupob.backenddesappapi.service.impl

import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import ar.unq.desapp.grupob.backenddesappapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailServiceimpl : UserDetailsService{

    @Autowired
    lateinit var userDao: UserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        var userdetails: UserEntity = userDao.findByEmail(username!!).orElseThrow {
            throw UsernameNotFoundException("El usuario no existe")
        }
        println("user: ${userdetails.email}")
        var authorities: Set<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_USER") ).toSet()
        return User(userdetails.email, userdetails.password, true, true, true, true, authorities)
    }
}
package ar.unq.desapp.grupob.backenddesappapi.security

import ar.unq.desapp.grupob.backenddesappapi.security.filters.JwtAuthenticationFilter
import ar.unq.desapp.grupob.backenddesappapi.security.filters.JwtAuthorizationFilter
import ar.unq.desapp.grupob.backenddesappapi.security.jwt.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @Autowired
    lateinit var jwtAuthorizationFilter: JwtAuthorizationFilter

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain{

        var jwtAuthenticationFilter: JwtAuthenticationFilter = JwtAuthenticationFilter(jwtUtils)
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager)
        jwtAuthenticationFilter.setFilterProcessesUrl("/login")
        return httpSecurity
            .csrf { it.disable() }
            .authorizeHttpRequests {  it.requestMatchers("/register", "/swagger-ui/**", "/api-docs/**").permitAll() }
            .authorizeHttpRequests { it.anyRequest().authenticated() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilter(jwtAuthenticationFilter)
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter :: class.java)
            .build()
    }

    @Bean
    fun authenticationManager(userDetailsService: UserDetailsService, passwordEncoder: PasswordEncoder): AuthenticationManager{
        var authenticationProvider: DaoAuthenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)
        return ProviderManager(authenticationProvider)
    }

    @Bean
    fun  passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }


}
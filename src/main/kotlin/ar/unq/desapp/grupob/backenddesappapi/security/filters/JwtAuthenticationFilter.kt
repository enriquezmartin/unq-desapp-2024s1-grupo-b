package ar.unq.desapp.grupob.backenddesappapi.security.filters

import ar.unq.desapp.grupob.backenddesappapi.model.UserEntity
import ar.unq.desapp.grupob.backenddesappapi.security.jwt.JwtUtils
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


class JwtAuthenticationFilter() : UsernamePasswordAuthenticationFilter() {

    lateinit var jwtUtils: JwtUtils

    constructor(jwtUtils: JwtUtils) : this() {
        this.jwtUtils = jwtUtils
    }


    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        var user : UserEntity? = null
        var username: String? = null
        var password: String? = null
        try{
            user = ObjectMapper().readValue(request!!.inputStream, UserEntity :: class.java)
            username = user.username
            password = user.password
        }catch (e: Exception){

        }

        var authenthicationToken: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(username, password)

        return authenticationManager.authenticate(authenthicationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        var user: User = authResult!!.principal as User
        var token: String = jwtUtils.generateAccessToken(user.username)

        response!!.addHeader("Authorization", token)

        var httpResponse :MutableMap<String, Any> = mutableMapOf()
        httpResponse.put("token", token)
        httpResponse.put("Message", "Autenticacion correcta")
        httpResponse.put("Username", user.username)

        response.writer.write(ObjectMapper().writeValueAsString(httpResponse))
        response.status = 200
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.flush()

        super.successfulAuthentication(request, response, chain, authResult)
    }
}
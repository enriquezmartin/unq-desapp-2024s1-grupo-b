package ar.unq.desapp.grupob.backenddesappapi.security.filters

import ar.unq.desapp.grupob.backenddesappapi.security.jwt.JwtUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthorizationFilter : OncePerRequestFilter(){

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var tokenHeader : String? = request.getHeader("Authorization")
        println(tokenHeader)
        println(request.requestURL)
        if( tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            var token: String = tokenHeader.substring(7)
            println(token)

            if(jwtUtils.isTokenValid(token)){
                println("entra aca")
                var username: String = jwtUtils.getUsernameFromToken(token)
                var userDetails: UserDetails = userDetailsService.loadUserByUsername(username)
                println(username)
                var authenticationToken = UsernamePasswordAuthenticationToken(username, null, userDetails.authorities)

                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        }

        filterChain.doFilter(request, response)

    }

}
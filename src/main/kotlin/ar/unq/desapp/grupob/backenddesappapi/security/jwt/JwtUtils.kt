package ar.unq.desapp.grupob.backenddesappapi.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtUtils {

    var secretKey: String = "xEnnEHFa535GJ5BKHdg9FThoPmnTJn+8SkRCLpeAXC21/gmZbV5XzZWix88yEFrG"
    var timeExpiration: String = "86400000"

    fun generateAccessToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + timeExpiration.toLong()))
            .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun getSignatureKey(): Key {
        var keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun isTokenValid(token: String): Boolean {
        try{
            Jwts.parser()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .body
            return true
        }catch (e: Exception){
            return false
        }
    }

    fun extractAllClaim(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(getSignatureKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun <T>getClaim(token: String, claimFunction : (Claims)-> T ): T {
        var claims = extractAllClaim(token)
        return claimFunction(claims)
    }

    fun getUsernameFromToken(token: String): String {
        return getClaim(token, Claims :: getSubject)
    }

}
package com.diagorn.sqltrainer.utils

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtUtils(
    @Value("\${sqltrainer.application.jwt.secret}")
    private val jwtSecret: String,
    @Value("\${sqltrainer.application.jwt.expireTimeMs}")
    private val expireTimeMs: Int,
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass.name)

    fun generate(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetails

        return Jwts.builder()
            .setSubject(userPrincipal.username)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + expireTimeMs))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun validate(jwtToken: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(jwtToken)
            return true
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: $jwtToken")
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: $jwtToken")
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT is unsupported: $jwtToken")
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: $jwtToken")
        }

        return false
    }

    fun getUserNameFromJwtToken(token: String): String? {
        return Jwts.parserBuilder().setSigningKey(key()).build()
            .parseClaimsJws(token).body.subject
    }

    private fun key() = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))
}
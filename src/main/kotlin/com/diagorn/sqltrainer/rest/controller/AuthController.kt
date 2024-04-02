package com.diagorn.sqltrainer.rest.controller

import com.diagorn.sqltrainer.model.mongo.User
import com.diagorn.sqltrainer.rest.dto.LoginRequest
import com.diagorn.sqltrainer.rest.dto.LoginResponse
import com.diagorn.sqltrainer.rest.dto.NewUserRequest
import com.diagorn.sqltrainer.service.user.UserService
import com.diagorn.sqltrainer.utils.JwtUtils
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Контроллер аутентификации
 *
 * @param userService - сервис работы с пользователями
 * @param authenticationManager - утилита аутентификации
 * @param jwtUtils - утилиты работы с JWT
 *
 * @author Diagorn
 */
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/register")
    fun register(@RequestBody newUserRequest: NewUserRequest) {
        logger.info("registering user: ${newUserRequest.email}")
        userService.registerUser(newUserRequest)
        logger.info("registering user ${newUserRequest.email} success")
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        logger.info("authenticating user: ${loginRequest.email}")
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication

        val jwt = jwtUtils.generate(authentication)
        val user = authentication.principal as User

        val response = ResponseEntity.ok(
            LoginResponse(
                token = jwt,
                userId = user.id.toString(),
                email = user.username,
                role = user.role.name
            )
        )
        logger.info("authenticating user success: ${loginRequest.email}")
        return response
    }
}
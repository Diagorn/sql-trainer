package com.diagorn.sqltrainer.rest.controller

import com.diagorn.sqltrainer.rest.dto.NewUserRequest
import com.diagorn.sqltrainer.service.user.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Контроллер аутентификации
 *
 * @param userService - сервис работы с пользователями
 *
 * @author Diagorn
 */
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody newUserRequest: NewUserRequest) = userService.registerUser(newUserRequest)
}
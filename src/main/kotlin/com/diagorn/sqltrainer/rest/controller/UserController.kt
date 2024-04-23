package com.diagorn.sqltrainer.rest.controller

import com.diagorn.sqltrainer.rest.dto.UserDto
import com.diagorn.sqltrainer.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    val userService: UserService,
) {

    @GetMapping
    fun getUsers(): ResponseEntity<List<UserDto>> = ResponseEntity.ok(userService.findUsers())

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<UserDto> =
        ResponseEntity.ok(userService.findById(UUID.fromString(id)).toDto())
}
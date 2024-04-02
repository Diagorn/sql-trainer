package com.diagorn.sqltrainer.service.user

import com.diagorn.sqltrainer.repo.UserRepo
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepo: UserRepo): UserDetailsService {
    override fun loadUserByUsername(email: String?): UserDetails {
        if (email == null) {
            throw BadCredentialsException("Пустое имя пользователя")
        }

        val user = userRepo.findByEmail(email)
        return user ?: throw BadCredentialsException("Не найден пользователь с e-mail $email")
    }
}
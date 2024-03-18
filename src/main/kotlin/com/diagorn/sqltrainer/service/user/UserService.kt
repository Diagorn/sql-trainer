package com.diagorn.sqltrainer.service.user

import com.diagorn.sqltrainer.rest.dto.EditUserRequest
import com.diagorn.sqltrainer.rest.dto.NewUserRequest
import com.diagorn.sqltrainer.rest.dto.UserDto
import java.util.UUID

/**
 * Сервис работы с пользователями
 *
 * @author Diagorn
 */
interface UserService {

    /**
     * Зарегистрировать нового пользователя
     *
     * @param request - запрос на регистрацию пользователя
     *
     * @return ид созданного пользователя
     */
    fun registerUser(request: NewUserRequest): UUID

    /**
     * Отредактировать пользователя
     *
     * @param request - запрос на редактирование пользователя
     *
     * @return отредактированный пользователь
     */
    fun editUser(request: EditUserRequest): UserDto
}
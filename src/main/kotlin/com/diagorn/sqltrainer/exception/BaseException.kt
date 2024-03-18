package com.diagorn.sqltrainer.exception

import org.springframework.http.HttpStatus

open class BaseException(
    message: String,
    val httpStatus: HttpStatus
) : RuntimeException(message)
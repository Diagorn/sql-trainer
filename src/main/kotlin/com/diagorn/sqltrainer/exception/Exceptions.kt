package com.diagorn.sqltrainer.exception

import org.springframework.http.HttpStatus.*

class BadSqlException(message: String): BaseException(message, BAD_REQUEST)

class ComparisonFailedException(message: String): BaseException(message, BAD_REQUEST)

class UnknownException(message: String): BaseException(message, UNPROCESSABLE_ENTITY)
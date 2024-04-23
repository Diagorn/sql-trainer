package com.diagorn.sqltrainer.rest

import com.diagorn.sqltrainer.exception.BaseException
import com.diagorn.sqltrainer.exception.ErrorResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CommonControllerAdvice {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): ResponseEntity<ErrorResponse> {
        logger.error(e.message)
        e.printStackTrace()
        val message = e.message ?: ""
        return ResponseEntity.status(e.httpStatus.value()).body(ErrorResponse(e.httpStatus.value(), message))
    }
}
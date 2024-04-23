package com.diagorn.sqltrainer.exception

data class ErrorResponse(
    private val statusCode: Int,
    private val message: String,
)

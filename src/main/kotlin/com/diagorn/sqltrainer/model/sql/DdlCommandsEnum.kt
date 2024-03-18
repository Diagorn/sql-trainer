package com.diagorn.sqltrainer.model.sql

enum class DdlCommandsEnum(
    private val lowerCaseCommandName: String
) {
    CREATE("create"),
    ALTER("alter"),
    DROP("drop"),
    TRUNCATE("truncate"),
    COMMENT("comments")
}
package com.diagorn.sqltrainer.model.sql

enum class DmlCommandsEnum(
    private val lowerCaseCommandName: String,
    private val modifyingData: Boolean
) {
    SELECT("select", false),
    INSERT("insert", true),
    UPDATE("update", true),
    DELETE("delete", true),
}
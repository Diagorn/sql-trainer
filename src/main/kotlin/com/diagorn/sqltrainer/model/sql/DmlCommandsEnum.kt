package com.diagorn.sqltrainer.model.sql

enum class DmlCommandsEnum(
    val lowerCaseCommandName: String,
    val modifyingData: Boolean
) {
    SELECT("select", false),
    INSERT("insert", true),
    UPDATE("update", true),
    DELETE("delete", true),
}
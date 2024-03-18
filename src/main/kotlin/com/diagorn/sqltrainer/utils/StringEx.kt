package com.diagorn.sqltrainer.utils

import com.diagorn.sqltrainer.model.sql.DdlCommandsEnum
import com.diagorn.sqltrainer.model.sql.DmlCommandsEnum
import com.diagorn.sqltrainer.model.sql.DmlCommandsEnum.*

fun String.containsAny(options: List<String>): Boolean {
    return options.filter { true }.any { option -> this.contains(option) }
}

fun String.validSelect(): Boolean {
    val uppercased = this.uppercase()

    // Проверяем, что начинается со слова SELECT
    if (!uppercased.startsWith(SELECT.name)) {
        return false
    }

    // Проверяем, что нет комманд кроме SELECT'а
    val hurtfulCommandNames = DmlCommandsEnum.entries.map { it.name.uppercase() }.toMutableList().also { list ->
        list.addAll(
            DdlCommandsEnum.entries.filter { it.name != SELECT.name }.map { it.name.uppercase() }
        )
    }
    return !uppercased.containsAny(hurtfulCommandNames)
}

fun String.validUpdate(): Boolean {
    val uppercased = this.uppercase()

    // Проверяем, что начинается со слова UPDATE
    if (!uppercased.startsWith(UPDATE.name)) {
        return false
    }

    // Проверяем, что нет других команд (SELECT допустим)
    val hurtfulCommandNames = DmlCommandsEnum.entries.map { it.name.uppercase() }.toMutableList().also { list ->
        list.addAll(
            DdlCommandsEnum.entries.filter { it.name != SELECT.name && it.name != UPDATE.name }.map { it.name.uppercase() }
        )
    }

    return !uppercased.containsAny(hurtfulCommandNames)
}

fun String.validInsert(): Boolean {
    val uppercased = this.uppercase()

    // Проверяем, что начинается со слова INSERT
    if (!uppercased.startsWith(INSERT.name)) {
        return false
    }

    // Проверяем, что нет других команд (SELECT допустим)
    val hurtfulCommandNames = DmlCommandsEnum.entries.map { it.name.uppercase() }.toMutableList().also { list ->
        list.addAll(
            DdlCommandsEnum.entries.filter { it.name != SELECT.name && it.name != INSERT.name }.map { it.name.uppercase() }
        )
    }

    return !uppercased.containsAny(hurtfulCommandNames)
}

fun String.validDelete(): Boolean {
    val uppercased = this.uppercase()

    // Проверяем, что начинается со слова DELETE
    if (!uppercased.startsWith(DELETE.name)) {
        return false
    }

    // Проверяем, что нет других команд (SELECT допустим)
    val hurtfulCommandNames = DmlCommandsEnum.entries.map { it.name.uppercase() }.toMutableList().also { list ->
        list.addAll(
            DdlCommandsEnum.entries.filter { it.name != SELECT.name && it.name != DELETE.name }.map { it.name.uppercase() }
        )
    }

    return !uppercased.containsAny(hurtfulCommandNames)
}
package com.diagorn.sqltrainer.exception

import org.springframework.http.HttpStatus.*

/**
 * Ошибка в выполнении SQL-скрипта
 */
class BadSqlException(message: String): BaseException(message, BAD_REQUEST)

/**
 * Ошибка в сравнении результатов проверки
 */
class ComparisonFailedException(message: String): BaseException(message, BAD_REQUEST)

/**
 * Неизвестная ошибка. Кидается во всех непредвиденных случаях
 */
class UnknownException(message: String): BaseException(message, UNPROCESSABLE_ENTITY)

/**
 * Ошибка некорректных полей в запросе
 */
class WrongFieldsException(message: String): BaseException(message, BAD_REQUEST)

/**
 * Ошибка ненайденного ресурса
 */
class NotFoundException(message: String): BaseException(message, NOT_FOUND)


/**
 * Ошибка под случаи когда в БД сохранено невалидное состояние
 * @see IllegalStateException
 */
class BadStateException(message: String): BaseException(message, BAD_REQUEST)
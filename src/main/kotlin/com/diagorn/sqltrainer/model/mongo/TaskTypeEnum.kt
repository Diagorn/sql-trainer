package com.diagorn.sqltrainer.model.mongo

import com.diagorn.sqltrainer.model.sql.DmlCommandsEnum

/**
 * Категории задач
 *
 * @param id - идентификатор категории
 * @param taskTypeName - наименование категории
 * @param sqlOperator - основной оператор, используемый в решении
 *
 * @author Diagorn
 */
enum class TaskTypeEnum(
    val id: Int,
    val taskTypeName: String,
    val sqlOperator: DmlCommandsEnum,
) {
    BASIC_SELECT(1, "Базовый SELECT", DmlCommandsEnum.SELECT),
    BASIC_INSERT(2, "Базовый INSERT", DmlCommandsEnum.INSERT),
    BASIC_UPDATE(3, "Базовый UPDATE", DmlCommandsEnum.UPDATE),
    BASIC_DELETE(4, "Базовый DELETE", DmlCommandsEnum.DELETE),

    SELECT_CONDITION(5, "SELECT с условиями", DmlCommandsEnum.SELECT),
    SELECT_SORTING(6, "SELECT с сортировкой", DmlCommandsEnum.SELECT),
    SELECT_GROUPING(7, "SELECT с группировкой", DmlCommandsEnum.SELECT),
    SELECT_SUBQUERY(8, "SELECT с подзапросом", DmlCommandsEnum.SELECT),
    SELECT_JOIN(9, "SELECT с соединением таблиц", DmlCommandsEnum.SELECT),
    SELECT_UNION(10, "SELECT с UNION", DmlCommandsEnum.SELECT),
    SELECT_INTERSECT(11, "SELECT с INTERSECT", DmlCommandsEnum.SELECT),
    SELECT_EXCEPT(12, "SELECT с EXCEPT", DmlCommandsEnum.SELECT),

    INSERT_SUBQUERY(13, "INSERT с подзапросом", DmlCommandsEnum.INSERT),
    UPDATE_SUBQUERY(14, "UPDATE с подзапросом", DmlCommandsEnum.UPDATE),
    DELETE_SUBQUERY(15, "DELETE с подзапросом", DmlCommandsEnum.DELETE);

    companion object {
        val TABLE_NEEDED = listOf(BASIC_INSERT, BASIC_DELETE, BASIC_UPDATE,
            INSERT_SUBQUERY, UPDATE_SUBQUERY, DELETE_SUBQUERY)

        val ORDER_IMPORTANT = listOf(SELECT_SORTING)

        fun ofId(id: Int): TaskTypeEnum {
            return entries.first { it.id == id }
        }
    }
}
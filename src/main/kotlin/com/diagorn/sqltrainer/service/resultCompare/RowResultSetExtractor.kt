package com.diagorn.sqltrainer.service.resultCompare

import com.diagorn.sqltrainer.model.common.Row
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class RowResultSetExtractor: ResultSetExtractor<List<Row>> {
    override fun extractData(rs: ResultSet) = Row.ofResultSet(rs)
}
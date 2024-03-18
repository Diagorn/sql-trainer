package com.diagorn.sqltrainer.service.resultCompare

import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
class RawResultSetExtractor: ResultSetExtractor<ResultSet> {
    override fun extractData(rs: ResultSet) = rs
}
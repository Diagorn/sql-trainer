package com.diagorn.sqltrainer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SqlTrainerApplication

fun main(args: Array<String>) {
	runApplication<SqlTrainerApplication>(*args)
}

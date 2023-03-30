package com.employees.domain

import com.file.Employee
import java.time.LocalDate

val csv = """
    EmpID, ProjectID, DateFrom, DateTo
    143, 12, 2013-11-01, 2014-01-05
    218, 10, 2012-05-16, NULL
    143, 10, 2009-01-01, 2011-04-27
""".trimIndent()

fun main() {
    println(
        extractCsvRows(csv).map {
            parseRow(it)
        }
    )
}

fun extractCsvRows(csv: String): List<String> {
    return csv.lines().drop(1)
}

fun parseRow(row: String): Employee? {
    val splittedArr = row.split(",").map {
        it.trim()
    }

    val empId = splittedArr[0].toIntOrNull() ?: return null
    val projectId = splittedArr[1].toIntOrNull() ?: return null
    val dateFrom = parseDate(splittedArr[2]) ?: return null
    val dateTo = parseDate(splittedArr[3]) ?: return null

    return Employee(empId, projectId, dateFrom, dateTo)
}

fun parseDate(date: String): LocalDate? {
    return LocalDate.now()
}
package com.employees.domain

import com.file.Employee
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun parseCsv(csv: String?): List<Employee?> {
    if (csv == null) {
        return emptyList()
    }

    return extractCsvRows(csv).map {
        parseRow(it)
    }
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
    val dateTo = parseDate(splittedArr[3]) ?: LocalDate.now()

    return Employee(empId, projectId, dateFrom, dateTo)
}

fun parseDate(date: String): LocalDate? {
    return try {
        LocalDate.parse(date)
    } catch (e: DateTimeParseException) {
        null
    }
}


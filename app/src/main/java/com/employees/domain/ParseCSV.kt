package com.employees.domain

import com.file.Employee
import java.time.LocalDate
import java.time.format.DateTimeParseException

/**
Returns a list of Employee objects for valid input of the form:
empId,projectId,dateFrom,dateTo
1,233,2013-11-01,2014-01-05
 */
fun parseCsv(csv: String?): List<Employee?> {
    if (csv == null) {
        return emptyList()
    }

    return extractCsvRows(csv).map {
        parseRow(it)
    }
}

/**
 * Returns a list of rows from the csv string, excluding the header row.
 */
fun extractCsvRows(csv: String): List<String> {
    return csv.lines().drop(1)
}

/**
 * Returns an Employee object for a valid row of the form:
 * empId,projectId,dateFrom,dateTo
 * 1,233,2013-11-01,2014-01-05
 */
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

/**
 * Returns a LocalDate object for a valid date string of the form: yyyy-MM-dd
 */
fun parseDate(date: String): LocalDate? {
    return try {
        LocalDate.parse(date)
    } catch (e: DateTimeParseException) {
        null
    }
}


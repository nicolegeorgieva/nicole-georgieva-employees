package com.employees.domain

import com.employees.domain.data.Employee
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
 *Parses a date string into a [LocalDate] object by attempting to match the input string
 *with a list of predefined date formats.
 *
 *@param dateString The date string to be parsed.
 *@return A [LocalDate] object if the input string matches one of the predefined date formats,
 * or **null** if no matching format is found.
 */
fun parseDate(dateString: String): LocalDate? {
    val dateFormats = listOf(
        "yyyy-MM-dd",
        "dd-MM-yyyy",
        "MM/dd/yyyy",
        "yyyy/MM/dd",
        "dd/MM/yyyy",
        "yyyyMMdd",
        "ddMMyyyy",
        "yyyy-M-d",
        "d-M-yyyy",
        "M/d/yyyy",
        "yyyy/M/d",
        "d/M/yyyy",
        "dd.MM.yyyy",
        "yyyy.MM.dd"
    )

    for (format in dateFormats) {
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(format))
        } catch (e: DateTimeParseException) {
            // Ignore and try the next format
        }
    }

    return null // Return null if no matching format is found
}


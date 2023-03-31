package com.employees.domain.data

import java.time.LocalDate

/**
 * Represents a Employee row from the CSV file that contains
 * the employee id, project id, date from and date to.
 */
data class Employee(
    val empId: Int,
    val projectId: Int,
    val dateFrom: LocalDate,
    val dateTo: LocalDate
)
package com.file

import java.time.LocalDate

data class Employee(
    val empId: Int,
    val projectId: Int,
    val dateFrom: LocalDate,
    val dateTo: LocalDate
)
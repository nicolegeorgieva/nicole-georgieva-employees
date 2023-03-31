package com.employees.domain.data

/**
 * Represents a pair of employees that have worked on the same project for a given number of days.
 */
data class EmployeesPair(
    val employee1: Employee,
    val employee2: Employee,
    val projectId: Int,
    val overlappingDays: Long
)
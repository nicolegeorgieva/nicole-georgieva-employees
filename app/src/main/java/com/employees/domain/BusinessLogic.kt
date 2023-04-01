package com.employees.domain

import com.employees.domain.data.Employee
import com.employees.domain.data.TaskResult
import java.time.LocalDate
import java.time.temporal.ChronoUnit

//res should be 1 and 2

fun main() {
    val employees = listOf(
        Employee(
            1, 10, LocalDate.of(2019, 1, 1),
            LocalDate.of(2019, 1, 14)
        ),
        Employee(
            2, 10, LocalDate.of(2019, 1, 1),
            LocalDate.of(2019, 1, 14)
        ),
        Employee(
            3, 20, LocalDate.of(2019, 1, 10),
            LocalDate.of(2019, 1, 28)
        ),
        Employee(
            4, 20, LocalDate.of(2019, 1, 10),
            LocalDate.of(2019, 1, 28)
        ),
        Employee(
            1, 30, LocalDate.of(2019, 1, 20),
            LocalDate.of(2019, 1, 28)
        ),
        Employee(
            2, 30, LocalDate.of(2019, 1, 20),
            LocalDate.of(2019, 1, 28)
        ),
    )

    val result = longestWorkingTogetherEmployees(employees)
    println(result)
}

fun longestWorkingTogetherEmployees(employees: List<Employee>): TaskResult? {
    val projectGroups = employees.groupBy { it.projectId }
    val pairDurations = mutableMapOf<Pair<Int, Int>, Long>()

    for ((_, group) in projectGroups) {
        calculatePairDurations(group, pairDurations)
    }

    val longestWorkingPair = pairDurations.maxByOrNull { it.value }

    return if (longestWorkingPair != null) {
        val (emp1Id, emp2Id) = longestWorkingPair.key
        val daysWorked = longestWorkingPair.value
        val commonProjects = findCommonProjects(employees, emp1Id, emp2Id)

        TaskResult(emp1Id, emp2Id, commonProjects.toList(), daysWorked)
    } else {
        null
    }
}

fun calculatePairDurations(group: List<Employee>, pairDurations: MutableMap<Pair<Int, Int>, Long>) {
    for (i in group.indices) {
        for (j in i + 1 until group.size) {
            val emp1 = group[i]
            val emp2 = group[j]

            val overlappingDuration = calculateOverlappingDuration(
                emp1.dateFrom, emp1.dateTo,
                emp2.dateFrom, emp2.dateTo
            )
            if (overlappingDuration > 0) {
                val pair = emp1.empId to emp2.empId
                pairDurations[pair] = pairDurations.getOrDefault(pair, 0) +
                        overlappingDuration
            }
        }
    }
}

fun findCommonProjects(employees: List<Employee>, emp1Id: Int, emp2Id: Int): Set<Int> {
    return employees
        .filter { it.empId == emp1Id || it.empId == emp2Id }
        .groupBy { it.projectId }
        .filter { it.value.size > 1 }
        .keys
}

fun calculateOverlappingDuration(
    date1From: LocalDate, date1To: LocalDate, date2From: LocalDate,
    date2To: LocalDate
): Long {
    val start = maxOf(date1From, date2From)
    val end = minOf(date1To, date2To)
    return if (start.isBefore(end) || start.isEqual(end)) ChronoUnit.DAYS.between(
        start,
        end
    ) + 1 else 0
}


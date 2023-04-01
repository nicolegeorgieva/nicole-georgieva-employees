package com.employees.domain

import com.employees.domain.data.Employee
import com.employees.domain.data.TaskResult
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Finds the pair of employees who have worked together on common projects
 * for the longest period of time.
 *
 * @param employees A list of [Employee] objects.
 * @return A [TaskResult] object containing the employee IDs, common project IDs, and days worked,
 *         or null if no matching pair is found.
 */
fun longestWorkingTogetherEmployees(employees: List<Employee>): TaskResult? {
    val projectGroups = employees.groupBy { it.projectId }
    val pairProjects = mutableMapOf<Pair<Int, Int>, MutableList<Pair<Int, Long>>>()

    for ((_, group) in projectGroups) {
        calculatePairProjects(group, pairProjects)
    }

    val longestWorkingPair =
        pairProjects.maxByOrNull { it.value.sumBy { project -> project.second.toInt() } }

    return if (longestWorkingPair != null) {
        val (emp1Id, emp2Id) = longestWorkingPair.key
        val projectsAndDays = longestWorkingPair.value
        val commonProjects = projectsAndDays.map { it.first }
        val daysWorked = projectsAndDays.map { it.second }
        TaskResult(emp1Id, emp2Id, commonProjects.toList(), daysWorked.toList())
    } else {
        null
    }
}

/**
 * Processes a list of employees working on a project and updates the pairProjects map with
 * the project ID and overlapping days for each pair of employees.
 *
 * @param group A list of [Employee] objects working on the same project.
 * @param pairProjects A mutable map containing pairs of employee IDs as keys and a list of
 *                     project ID - overlapping days pairs as values.
 */
fun calculatePairProjects(
    group: List<Employee>,
    pairProjects: MutableMap<Pair<Int, Int>, MutableList<Pair<Int, Long>>>
) {
    for (i in group.indices) {
        for (j in i + 1 until group.size) {
            val emp1 = group[i]
            val emp2 = group[j]

            val overlappingDuration =
                calculateOverlappingDuration(emp1.dateFrom, emp1.dateTo, emp2.dateFrom, emp2.dateTo)
            if (overlappingDuration > 0) {
                val pair = emp1.empId to emp2.empId
                val projectId = emp1.projectId
                pairProjects.getOrPut(pair) { mutableListOf() }
                    .add(Pair(projectId, overlappingDuration))
            }
        }
    }
}

/**
 * Calculates the overlapping days between two date ranges.
 *
 * @param date1From The start date of the first date range.
 * @param date1To The end date of the first date range.
 * @param date2From The start date of the second date range.
 * @param date2To The end date of the second date range.
 * @return The number of overlapping days between the two date ranges, or 0 if there is no overlap.
 */
fun calculateOverlappingDuration(
    date1From: LocalDate,
    date1To: LocalDate,
    date2From: LocalDate,
    date2To: LocalDate
): Long {
    val start = maxOf(date1From, date2From)
    val end = minOf(date1To, date2To)
    return if (start.isBefore(end) || start.isEqual(end)) ChronoUnit.DAYS.between(
        start,
        end
    ) + 1 else 0
}
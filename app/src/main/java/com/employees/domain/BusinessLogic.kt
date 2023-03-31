package com.employees.domain

import com.employees.domain.data.Employee
import com.employees.domain.data.EmployeesPair
import java.time.Duration

/**
 * Returns a map of project ids to employees that are assigned to that project.
 * Only projects with more than one employee are included.
 */
fun findEmployeesWithSameProjectId(employees: List<Employee?>): Map<Int, List<Employee>> {
    return employees.filterNotNull()
        .groupBy { it.projectId }
        .filter { (_, employeesInProject) -> employeesInProject.size > 1 }
}

/**
 * Implement findEmployeesWithSameProjectId using the provided parseCsv function.
 */
fun employeesWithSameProjectId(fileContent: String): Map<Int, List<Employee>> {
    return findEmployeesWithSameProjectId(parseCsv(fileContent))
}

/**
 * Returns a map of project ids to employees that are assigned to that project and have overlapping time.
 * Only projects with more than one employee are included.
 */
fun employeesWithSameProjectAndTime(fileContent: String): Map<Int, List<Employee>> {
    return filterEmployeesWithSameTime(employeesWithSameProjectId(fileContent))
}

/**
 * Returns a map of project ids to employees that have overlapping time.
 * Only projects with more than one employee are included.
 */
fun filterEmployeesWithSameTime(employees: Map<Int, List<Employee>>): Map<Int, List<Employee>> {
    return employees.mapValues { (_, employeesList) ->
        employeesList.filter { employee1 ->
            employeesList.any { employee2 ->
                employee1 != employee2 && employee1.dateFrom <= employee2.dateTo &&
                        employee1.dateTo >= employee2.dateFrom
            }
        }
    }.filterValues { it.isNotEmpty() }
}

fun formatResult(pair: EmployeesPair?): String {
    if (pair == null) return "there are no employees with overlapping time"
    return "${pair.employee1.empId}, ${pair.employee2.empId}, ${pair.overlappingDays}"
}


/**
 * Returns the pair of employees that have worked on the same project for the longest time.
 * If there are multiple pairs with the same longest time, return any of them.
 * If there are no employees with overlapping time, return null.
 */
fun longestWorkingPair(fileContent: String): EmployeesPair? {
    val sameProjectAndTimeEmployees = employeesWithSameProjectAndTime(fileContent)
    var maxOverlap: Long = 0
    var longestPair: EmployeesPair? = null

    for ((_, employees) in sameProjectAndTimeEmployees) {
        for (i in employees.indices) {
            for (j in i + 1 until employees.size) {
                val overlap = overlappingDays(employees[i], employees[j])
                if (overlap > maxOverlap) {
                    maxOverlap = overlap
                    longestPair = EmployeesPair(employees[i], employees[j], maxOverlap)
                }
            }
        }
    }

    return longestPair
}

/**
 * Returns the number of days that the two employees have worked on the same project.
 * If the employees have no overlapping time, return 0.
 */
fun overlappingDays(emp1: Employee, emp2: Employee): Long {
    val start = maxOf(emp1.dateFrom, emp2.dateFrom)
    val end = minOf(emp1.dateTo, emp2.dateTo)

    return if (start.isBefore(end) || start.isEqual(end)) {
        Duration.between(start.atStartOfDay(), end.atStartOfDay()).toDays()
    } else {
        0
    }
}


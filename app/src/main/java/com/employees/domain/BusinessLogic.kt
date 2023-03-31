package com.employees.domain

import com.file.Employee

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
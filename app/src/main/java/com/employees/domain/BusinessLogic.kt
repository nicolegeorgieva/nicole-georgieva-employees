package com.employees.domain

import com.file.Employee

fun findEmployeesWithSameProjectId(employees: List<Employee?>): Map<Int, List<Employee>> {
    return employees.filterNotNull()
        .groupBy { it.projectId }
        .filter { (_, employeesInProject) -> employeesInProject.size > 1 }
}

val employeesWithSameProjectId = findEmployeesWithSameProjectId(listOfEmployees)

fun main() {
    println(employeesWithSameProjectId)
}
package com.employees.domain.data

data class TaskResult(
    val employee1Id: Int,
    val employee2Id: Int,
    val commonProjects: List<Int>,
    val daysWorked: List<Long>
)
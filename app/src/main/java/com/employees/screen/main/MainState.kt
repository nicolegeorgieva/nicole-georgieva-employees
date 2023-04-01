package com.employees.screen.main

import com.employees.domain.data.TaskResult

data class MainState(
    val fileImported: Boolean,
    val result: TaskResult?
)
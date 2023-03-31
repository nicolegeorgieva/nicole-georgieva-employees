package com.employees.screen.main

import com.employees.domain.data.EmployeesPair

data class MainState(
    val fileImported: Boolean,
    val result: EmployeesPair?
)
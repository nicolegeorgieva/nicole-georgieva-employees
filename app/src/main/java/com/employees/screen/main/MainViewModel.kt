package com.employees.screen.main

import android.annotation.SuppressLint
import android.content.Context
import com.employees.base.FlowViewModel
import com.employees.domain.data.EmployeesPair
import com.employees.domain.longestWorkingPair
import com.file.readFile
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : FlowViewModel<MainState, MainEvent>() {
    override val initialUi = MainState(
        fileImported = false,
        result = null
    )

    private val fileImported = MutableStateFlow<Boolean>(false)
    private val result = MutableStateFlow<EmployeesPair?>(null)

    override val uiFlow = combine(
        fileImported,
        result
    ) { fileImported, result ->
        MainState(
            fileImported = fileImported,
            result = result
        )
    }

    override suspend fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.FilePicked -> {
                fileImported.value = true

                val fileString = readFile(context, event.file)

                val employeesPair = longestWorkingPair(fileString ?: "")

                result.value = employeesPair
            }
        }
    }
}
package com.employees.screen.main

import android.annotation.SuppressLint
import android.content.Context
import com.employees.base.FlowViewModel
import com.employees.domain.employeesWithSameProjectId
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
        fileContent = null,
        result = null
    )

    private val fileContent = MutableStateFlow<String?>(null)
    private val result = MutableStateFlow<String?>(null)

    override val uiFlow = combine(
        fileContent,
        result
    ) { fileContent, result ->
        MainState(
            fileContent = fileContent,
            result = result
        )
    }

    override suspend fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.FilePicked -> {
                fileContent.value = readFile(context, event.file)
                result.value = "Result: ${employeesWithSameProjectId(fileContent.value ?: "")}"
            }
            else -> {}
        }
    }
}
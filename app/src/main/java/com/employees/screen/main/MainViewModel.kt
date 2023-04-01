package com.employees.screen.main

import android.annotation.SuppressLint
import android.content.Context
import com.employees.base.FlowViewModel
import com.employees.domain.data.TaskResult
import com.employees.domain.longestWorkingTogetherEmployees
import com.employees.domain.parseCsv
import com.employees.file.readFile
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
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
    private val result = MutableStateFlow<TaskResult?>(null)

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
                try {
                    val fileString = withContext(Dispatchers.IO) {
                        // run it on an IO thread
                        readFile(context, event.file)
                    }
                    fileImported.value = true

                    val employeesPair = withContext(Dispatchers.Default) {
                        // run it on a computational thread
                        longestWorkingTogetherEmployees(
                            parseCsv(fileString).filterNotNull()
                        )
                    }

                    result.value = employeesPair
                } catch (e: Exception) {
                    fileImported.value = false
                }
            }
        }
    }
}
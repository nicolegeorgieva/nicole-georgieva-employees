package com.employees.screen.main

import android.annotation.SuppressLint
import android.content.Context
import com.employees.base.FlowViewModel
import com.employees.domain.formatResult
import com.employees.domain.longestWorkingPair
import com.file.readFile
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
) : FlowViewModel<MainState, MainEvent>() {
    override val initialUi = MainState(
        result = null
    )

    private val result = MutableStateFlow<String?>(null)

    override val uiFlow = result.map { result ->
        MainState(
            result = result
        )
    }

    override suspend fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.FilePicked -> {
                val fileString = readFile(context, event.file)

                val employeesPair = longestWorkingPair(fileString ?: "")

                result.value = formatResult(employeesPair)
            }
        }
    }
}
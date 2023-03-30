package com.employees.screen.main

import com.employees.base.FlowViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : FlowViewModel<MainState, MainEvent>() {
    override val initialUi = MainState(
        fileContent = null,
        result = null
    )

    private val fileContent = MutableStateFlow<String?>(null)

    override val uiFlow = fileContent.map {
        MainState(fileContent = it, result = null)
    }

    override suspend fun handleEvent(event: MainEvent) {
        when (event) {

            else -> {}
        }
    }
}
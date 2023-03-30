package com.employees.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.employees.MainActivity

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun UI(
    state: MainState,
    onEvent: (MainEvent) -> Unit
) {
    Column() {
        val mainActivity = LocalContext.current as MainActivity

        Button(onClick = {
            mainActivity.fileChooser {
                onEvent(MainEvent.FilePicked(file = it))
            }
        }) {
            Text(text = "Pick")
        }

        Text(text = "${state.fileContent}")
    }
}
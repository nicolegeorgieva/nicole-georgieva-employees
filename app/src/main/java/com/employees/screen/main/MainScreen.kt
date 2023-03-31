package com.employees.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        val mainActivity = LocalContext.current as MainActivity

        Text(text = "Welcome to Employees!")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            mainActivity.fileChooser {
                onEvent(MainEvent.FilePicked(file = it))
            }
        }) {
            Text(text = "Export CSV*")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "* to see pair of employees who have worked together for the" +
                    " longest period of time"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "${state.fileContent}")

        Text(text = "${state.result}")
    }
}
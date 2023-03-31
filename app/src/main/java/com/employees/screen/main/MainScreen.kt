package com.employees.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val mainActivity = LocalContext.current as MainActivity

        Text(text = "Welcome to Employees!", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                mainActivity.fileChooser {
                    onEvent(MainEvent.FilePicked(file = it))
                }
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Import CSV*")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "* to see pair of employees who have worked together for the" +
                    " longest period of time",
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (state.result != null) {
            ResultText(label = "Employee 1 ID:", value = state.result.employee1.empId)
            ResultText(label = "Employee 2 ID:", value = state.result.employee2.empId)
            ResultText(label = "Overlapping days:", value = state.result.overlappingDays.toInt())
        } else if (state.fileImported) {
            Text(
                text = "There isn't a result matching the criteria.",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ResultText(label: String, value: Int) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = value.toString(),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}
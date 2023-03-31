package com.employees.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import com.employees.domain.data.EmployeesPair

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
    val mainActivity = LocalContext.current as MainActivity

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item(key = "welcome message") {
            Text(text = "Welcome to Employees!", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(24.dp))
        }

        item(key = "import csv button") {
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
        }

        item(key = "explanation text") {
            Text(
                text = "* to see pair of employees who have worked together on the same project" +
                        " for the longest period of time.",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        item(key = "result") {
            if (state.result != null) {
                ResultGrid(result = state.result)
            } else if (state.fileImported) {
                Text(
                    text = "There isn't a result matching the criteria.",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ResultGrid(result: EmployeesPair) {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outline
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(modifier = Modifier.fillMaxWidth()) {
        GridTitle(modifier = Modifier.weight(1f), label = "Employee ID #1")
        GridTitle(modifier = Modifier.weight(1f), label = "Employee ID #2")
        GridTitle(modifier = Modifier.weight(1f), label = "Project ID")
        GridTitle(modifier = Modifier.weight(1f), label = "Days worked")
    }

    Spacer(modifier = Modifier.height(8.dp))

    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outline
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(modifier = Modifier.fillMaxWidth()) {
        ResultText(modifier = Modifier.weight(1f), value = result.employee1.empId)
        ResultText(modifier = Modifier.weight(1f), value = result.employee2.empId)
        ResultText(modifier = Modifier.weight(1f), value = result.projectId)
        ResultText(
            modifier = Modifier.weight(1f),
            value = result.overlappingDays.toInt()
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outline
    )
}

@Composable
fun GridTitle(modifier: Modifier, label: String) {
    Text(
        modifier = modifier,
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun ResultText(modifier: Modifier, value: Int) {
    Text(
        modifier = modifier,
        text = value.toString(),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold
    )
}
package com.employees.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.employees.domain.data.TaskResult

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
                text = "* to see pair of employees who have worked together on common projects" +
                        " for the longest period of time.",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        if (state.result != null) {
            item(key = "header") {
                ResultGridHeader()
            }

            itemsIndexed(
                state.result.commonProjects,
                key = { index, _ -> index }
            ) { index, _ ->
                ResultRow(result = state.result, index = index)
            }
        } else if (state.fileImported) {
            item(key = "no-result") {
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
fun ResultGridHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(vertical = 8.dp)
    ) {
        GridTitle(modifier = Modifier.weight(1f), label = "Employee ID #1")
        GridTitle(modifier = Modifier.weight(1f), label = "Employee ID #2")
        GridTitle(modifier = Modifier.weight(1f), label = "Project ID")
        GridTitle(modifier = Modifier.weight(1f), label = "Days worked")
    }
}

@Composable
fun ResultRow(result: TaskResult, index: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (index % 2 == 0) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.05f
                )
            )
            .padding(vertical = 8.dp)
    ) {
        ResultText(modifier = Modifier.weight(1f), value = result.employee1Id.toString())
        ResultText(modifier = Modifier.weight(1f), value = result.employee2Id.toString())
        ResultText(
            modifier = Modifier.weight(1f),
            value = result.commonProjects[index].toString()
        )
        ResultText(
            modifier = Modifier.weight(1f),
            value = result.daysWorked[index].toString()
        )
    }
}

@Composable
fun GridTitle(modifier: Modifier, label: String) {
    Text(
        modifier = modifier,
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSecondary
    )
}

@Composable
fun ResultText(modifier: Modifier, value: String) {
    Text(
        modifier = modifier,
        text = value,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold
    )
}
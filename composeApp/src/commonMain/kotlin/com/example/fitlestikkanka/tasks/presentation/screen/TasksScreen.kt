package com.example.fitlestikkanka.tasks.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fitlestikkanka.tasks.domain.model.Task
import com.example.fitlestikkanka.tasks.presentation.viewmodel.TasksUiState
import com.example.fitlestikkanka.tasks.presentation.viewmodel.TasksViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Tasks screen - displays list of user tasks.
 *
 * Stateful composable that owns TasksViewModel.
 * Follows state hoisting pattern: state flows down, events flow up.
 */
@Composable
fun TasksScreen(
    viewModel: TasksViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    TasksScreenContent(
        uiState = uiState,
        onRetry = viewModel::retry
    )
}

@Composable
private fun TasksScreenContent(
    uiState: TasksUiState,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        when (uiState) {
            is TasksUiState.Loading -> LoadingState()
            is TasksUiState.Success -> TasksList(tasks = uiState.tasks)
            is TasksUiState.Empty -> EmptyState()
            is TasksUiState.Error -> ErrorState(
                message = uiState.message,
                onRetry = onRetry
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color(0xFF128C7E))
    }
}

@Composable
private fun TasksList(tasks: List<Task>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tasks) { task ->
            TaskCard(task = task)
        }
    }
}

@Composable
private fun TaskCard(task: Task) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )
    ) {
        Text(
            text = task.description,
            modifier = Modifier.padding(16.dp),
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Henüz görev yok",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Gray
            )
            Text(
                text = "Görevleriniz buradan görüntülenecek",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF128C7E)
                )
            ) {
                Text("Tekrar Dene", color = Color.White)
            }
        }
    }
}

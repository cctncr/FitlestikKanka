package com.example.fitlestikkanka.debts.presentation.screen

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
import com.example.fitlestikkanka.debts.domain.model.Debt
import com.example.fitlestikkanka.debts.presentation.viewmodel.DebtsUiState
import com.example.fitlestikkanka.debts.presentation.viewmodel.DebtsViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Debts screen - displays list of user debts.
 *
 * Stateful composable that owns DebtsViewModel.
 * Follows state hoisting pattern: state flows down, events flow up.
 */
@Composable
fun DebtsScreen(
    viewModel: DebtsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    DebtsScreenContent(
        uiState = uiState,
        onRetry = viewModel::retry
    )
}

@Composable
private fun DebtsScreenContent(
    uiState: DebtsUiState,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        when (uiState) {
            is DebtsUiState.Loading -> LoadingState()
            is DebtsUiState.Success -> DebtsList(debts = uiState.debts)
            is DebtsUiState.Empty -> EmptyState()
            is DebtsUiState.Error -> ErrorState(
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
private fun DebtsList(debts: List<Debt>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(debts) { debt ->
            DebtCard(debt = debt)
        }
    }
}

@Composable
private fun DebtCard(debt: Debt) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )
    ) {
        Text(
            text = debt.description,
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
                text = "Henüz borç yok",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Gray
            )
            Text(
                text = "Borçlarınız buradan görüntülenecek",
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

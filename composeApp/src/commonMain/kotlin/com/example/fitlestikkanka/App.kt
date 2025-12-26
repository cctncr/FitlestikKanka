package com.example.fitlestikkanka

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.fitlestikkanka.auth.presentation.screen.UserSelectionScreen
import com.example.fitlestikkanka.auth.presentation.viewmodel.AuthUiState
import com.example.fitlestikkanka.auth.presentation.viewmodel.AuthViewModel
import com.example.fitlestikkanka.chat.presentation.screen.ChatScreen
import com.example.fitlestikkanka.core.components.BottomNavigationBar
import com.example.fitlestikkanka.core.navigation.Screen
import com.example.fitlestikkanka.debts.presentation.screen.DebtsScreen
import com.example.fitlestikkanka.tasks.presentation.screen.TasksScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

/**
 * Main App composable.
 * Manages authentication flow.
 *
 * Koin is initialized in platform-specific Application class (Android)
 * or in main() function (other platforms).
 *
 * Flow:
 * - Unauthenticated → UserSelectionScreen
 * - Authenticated → AppContent with bottom navigation
 */
@Composable
@Preview
fun App() {
    MaterialTheme {
        AuthenticationFlow()
    }
}

@Composable
private fun AuthenticationFlow() {
    val authViewModel: AuthViewModel = koinInject()
    val authState by authViewModel.uiState.collectAsState()

    when (val state = authState) {
        is AuthUiState.Unauthenticated -> {
            UserSelectionScreen(
                onUserSelected = { username ->
                    // Hardcoded password for both users
                    authViewModel.login(username, "123456")
                }
            )
        }
        is AuthUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is AuthUiState.Authenticated -> {
            AppContent(
                currentUserId = state.user.id.toString(),
                username = state.user.username
            )
        }
        is AuthUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error: ${state.message}")
                    // Could add retry button here
                }
            }
        }
    }
}

@Composable
private fun AppContent(currentUserId: String, username: String) {
    // Manual screen state management
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Chat) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentScreen = currentScreen,
                onScreenSelected = { screen -> currentScreen = screen }
            )
        },
        containerColor = Color(0xFF121212)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(paddingValues)
        ) {
            // Screen switching based on current state
            when (currentScreen) {
                Screen.Chat -> ChatScreen(
                    conversationId = "main-conversation",
                    currentUserId = currentUserId,
                    onNavigateBack = { /* Optional: handle back if needed */ }
                )
                Screen.Tasks -> TasksScreen()
                Screen.Debts -> DebtsScreen()
            }
        }
    }
}

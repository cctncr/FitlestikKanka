package com.example.fitlestikkanka

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.fitlestikkanka.chat.presentation.screen.ChatScreen
import com.example.fitlestikkanka.core.components.BottomNavigationBar
import com.example.fitlestikkanka.core.di.appModule
import com.example.fitlestikkanka.core.di.chatModule
import com.example.fitlestikkanka.core.di.debtsModule
import com.example.fitlestikkanka.core.di.platformModule
import com.example.fitlestikkanka.core.di.tasksModule
import com.example.fitlestikkanka.core.navigation.Screen
import com.example.fitlestikkanka.debts.presentation.screen.DebtsScreen
import com.example.fitlestikkanka.tasks.presentation.screen.TasksScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

/**
 * Main App composable.
 * Initializes Koin dependency injection and sets up bottom navigation.
 *
 * Uses manual screen state management with sealed interface Screen.
 * Three main screens: Chat, Tasks (Görevler), Debts (Borçlar).
 */
@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(
                platformModule,
                appModule,
                chatModule,
                tasksModule,  // NEW: Tasks feature module
                debtsModule   // NEW: Debts feature module
            )
        }
    ) {
        MaterialTheme {
            AppContent()
        }
    }
}

@Composable
private fun AppContent() {
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
                    conversationId = "demo-conversation-1",
                    currentUserId = "current-user",
                    onNavigateBack = { /* Optional: handle back if needed */ }
                )
                Screen.Tasks -> TasksScreen()
                Screen.Debts -> DebtsScreen()
            }
        }
    }
}

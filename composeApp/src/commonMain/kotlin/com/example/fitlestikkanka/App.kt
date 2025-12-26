package com.example.fitlestikkanka

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fitlestikkanka.chat.presentation.screen.ChatScreen
import com.example.fitlestikkanka.core.di.appModule
import com.example.fitlestikkanka.core.di.chatModule
import com.example.fitlestikkanka.core.di.platformModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

/**
 * Main App composable.
 * Initializes Koin dependency injection and sets up navigation.
 *
 * For demo purposes, provides a button to navigate to ChatScreen.
 * In a real app, this would use proper navigation (Voyager, Decompose, etc.)
 */
@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(platformModule, appModule, chatModule)
        }
    ) {
        MaterialTheme {
            var showChat by remember { mutableStateOf(false) }

            if (showChat) {
                // ChatScreen with mock data
                ChatScreen(
                    conversationId = "demo-conversation-1",
                    currentUserId = "current-user",
                    onNavigateBack = { showChat = false }
                )
            } else {
                // Home screen with button to open chat
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF121212))
                        .safeContentPadding()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "FitlestikKanka Chat",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { showChat = true },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF128C7E)
                        )
                    ) {
                        Text(
                            text = "Open Chat Screen",
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "WhatsApp-style 1-to-1 chat\nKotlin Multiplatform • MVVM • Clean Architecture",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
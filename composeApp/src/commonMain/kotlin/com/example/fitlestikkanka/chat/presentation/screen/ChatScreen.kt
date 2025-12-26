package com.example.fitlestikkanka.chat.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fitlestikkanka.chat.presentation.components.*
import com.example.fitlestikkanka.chat.presentation.viewmodel.*
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Main chat screen - the ONLY stateful composable in the chat feature.
 * Owns the ChatViewModel and orchestrates the entire chat UI.
 *
 * Responsibilities:
 * - Observes ChatViewModel's UI state
 * - Routes UI events to ViewModel
 * - Composes all stateless child composables
 * - Handles navigation
 *
 * Follows MVVM + State Hoisting pattern:
 * - State flows DOWN from ViewModel to UI
 * - Events flow UP from UI to ViewModel
 *
 * @param conversationId ID of the conversation to display
 * @param currentUserId ID of the currently logged-in user
 * @param onNavigateBack Callback to navigate back to previous screen
 * @param viewModel ChatViewModel injected by Koin with parameters
 */
@Composable
fun ChatScreen(
    conversationId: String,
    currentUserId: String,
    onNavigateBack: () -> Unit,
    viewModel: ChatViewModel = koinViewModel { parametersOf(conversationId, currentUserId) }
) {
    val uiState by viewModel.uiState.collectAsState()

    ChatScreenContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack
    )
}

/**
 * Content composable that renders UI based on state.
 * Separated from ChatScreen for better testability.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreenContent(
    uiState: ChatUiState,
    onEvent: (ChatUiEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            when (uiState) {
                is ChatUiState.Success -> ChatHeader(
                    participant = uiState.otherParticipant,
                    isTyping = uiState.isOtherUserTyping,
                    onNavigateBack = onNavigateBack
                )
                else -> TopAppBar(
                    title = { Text("Chat", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF1E1E1E)
                    )
                )
            }
        },
        bottomBar = {
            if (uiState is ChatUiState.Success) {
                MessageInput(
                    onSendMessage = { content ->
                        onEvent(ChatUiEvent.SendMessage(content))
                    },
                    onTypingStatusChanged = { isTyping ->
                        onEvent(ChatUiEvent.TypingStatusChanged(isTyping))
                    }
                )
            }
        },
        containerColor = Color(0xFF121212) // Dark background
    ) { paddingValues ->
        when (uiState) {
            is ChatUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(Color(0xFF121212)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF128C7E)
                    )
                }
            }

            is ChatUiState.Success -> {
                MessageList(
                    messageGroups = uiState.messages,
                    currentUserId = uiState.currentUserId,
                    onMessageRead = { messageId ->
                        onEvent(ChatUiEvent.MessageRead(messageId))
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(Color(0xFF121212))
                )
            }

            is ChatUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(Color(0xFF121212)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = uiState.message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                        if (uiState.canRetry) {
                            Button(
                                onClick = { onEvent(ChatUiEvent.Retry) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF128C7E)
                                )
                            ) {
                                Text("Retry", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

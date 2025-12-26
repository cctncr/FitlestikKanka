package com.example.fitlestikkanka.chat.presentation.viewmodel

import com.example.fitlestikkanka.chat.domain.model.Message
import com.example.fitlestikkanka.chat.domain.model.Participant

/**
 * Represents the UI state for the Chat screen.
 * Follows a sealed interface pattern for type-safe state handling.
 */
sealed interface ChatUiState {
    /**
     * Initial loading state when the chat screen is first opened
     */
    data object Loading : ChatUiState

    /**
     * Success state when messages are loaded and ready to display
     *
     * @property messages List of message groups organized by date
     * @property otherParticipant The other user in the 1-to-1 conversation
     * @property isConnected Whether WebSocket connection is active
     * @property isOtherUserTyping Whether the other user is currently typing
     * @property currentUserId ID of the current logged-in user
     */
    data class Success(
        val messages: List<MessageGroup>,
        val otherParticipant: Participant?,
        val isConnected: Boolean = true,
        val isOtherUserTyping: Boolean = false,
        val currentUserId: String
    ) : ChatUiState

    /**
     * Error state when something goes wrong (network error, etc.)
     *
     * @property message Error message to display to user
     * @property canRetry Whether the user can retry the operation
     */
    data class Error(
        val message: String,
        val canRetry: Boolean = true
    ) : ChatUiState
}

/**
 * Represents a group of messages for a specific date.
 * Used to display date separators in the chat UI.
 *
 * @property date Human-readable date string ("Today", "Yesterday", "Jan 15, 2025")
 * @property messages List of messages for this date, sorted by timestamp
 */
data class MessageGroup(
    val date: String,
    val messages: List<Message>
)

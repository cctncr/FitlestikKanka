package com.example.fitlestikkanka.chat.presentation.viewmodel

/**
 * Represents user interactions and events in the Chat screen.
 * Follows the unidirectional data flow pattern (events flow UP to ViewModel).
 */
sealed interface ChatUiEvent {
    /**
     * User wants to send a message
     *
     * @property content The text content of the message to send
     */
    data class SendMessage(val content: String) : ChatUiEvent

    /**
     * User has read a message (displayed on screen)
     *
     * @property messageId ID of the message that was read
     */
    data class MessageRead(val messageId: String) : ChatUiEvent

    /**
     * User wants to delete a message
     *
     * @property messageId ID of the message to delete
     */
    data class DeleteMessage(val messageId: String) : ChatUiEvent

    /**
     * User wants to retry after an error
     */
    data object Retry : ChatUiEvent

    /**
     * User wants to mark all messages as read
     */
    data object MarkAllAsRead : ChatUiEvent

    /**
     * User's typing status has changed
     *
     * @property isTyping Whether the user is currently typing
     */
    data class TypingStatusChanged(val isTyping: Boolean) : ChatUiEvent
}

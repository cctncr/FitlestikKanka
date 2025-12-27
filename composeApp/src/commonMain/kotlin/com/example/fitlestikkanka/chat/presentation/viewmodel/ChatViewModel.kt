package com.example.fitlestikkanka.chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlestikkanka.chat.domain.model.MessageStatus
import com.example.fitlestikkanka.chat.domain.model.Participant
import com.example.fitlestikkanka.chat.domain.repository.MessageRepository
import com.example.fitlestikkanka.chat.domain.usecase.LoadConversationUseCase
import com.example.fitlestikkanka.chat.domain.usecase.ObserveMessagesUseCase
import com.example.fitlestikkanka.chat.domain.usecase.SendMessageUseCase
import com.example.fitlestikkanka.chat.domain.usecase.UpdateMessageStatusUseCase
import com.example.fitlestikkanka.core.util.MessageGrouper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for the Chat screen.
 * Manages UI state and coordinates between Use Cases and UI.
 *
 * Follows MVVM architecture with:
 * - Unidirectional data flow (state down, events up)
 * - StateFlow for reactive state management
 * - Use cases for business logic
 *
 * @property conversationId ID of the conversation being viewed
 * @property currentUserId ID of the currently logged-in user
 * @property messageRepository Repository for fetching message history
 * @property sendMessageUseCase Use case for sending messages
 * @property observeMessagesUseCase Use case for observing message updates
 * @property updateMessageStatusUseCase Use case for updating message status
 * @property loadConversationUseCase Use case for loading conversation metadata
 * @property messageGrouper Utility for grouping messages by date
 */
class ChatViewModel(
    private val conversationId: String,
    private val currentUserId: String,
    private val messageRepository: MessageRepository,
    private val sendMessageUseCase: SendMessageUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val updateMessageStatusUseCase: UpdateMessageStatusUseCase,
    private val loadConversationUseCase: LoadConversationUseCase,
    private val messageGrouper: MessageGrouper
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Loading)
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private var otherParticipant: Participant? = null

    init {
        loadConversation()
        syncMessages()
        observeMessages()
    }

    /**
     * Handles UI events from the Chat screen.
     * Routes events to appropriate private methods.
     */
    fun onEvent(event: ChatUiEvent) {
        when (event) {
            is ChatUiEvent.SendMessage -> sendMessage(event.content)
            is ChatUiEvent.MessageRead -> markMessageAsRead(event.messageId)
            is ChatUiEvent.DeleteMessage -> deleteMessage(event.messageId)
            is ChatUiEvent.Retry -> retry()
            is ChatUiEvent.MarkAllAsRead -> markAllAsRead()
            is ChatUiEvent.TypingStatusChanged -> updateTypingStatus(event.isTyping)
        }
    }

    /**
     * Loads conversation metadata (participants, etc.)
     */
    private fun loadConversation() {
        viewModelScope.launch {
            loadConversationUseCase(conversationId)
                .onSuccess { conversation ->
                    otherParticipant = conversation.otherParticipant
                }
                .onFailure { error ->
                    _uiState.value = ChatUiState.Error(
                        message = error.message ?: "Failed to load conversation"
                    )
                }
        }
    }

    /**
     * Syncs message history from server.
     * Fetches recent messages and saves them to local DB.
     */
    fun syncMessages() {
        viewModelScope.launch {
            println("DEBUG: ChatViewModel - Starting syncMessages for conversation: $conversationId")
            messageRepository.syncMessages(conversationId)
                .onSuccess {
                    println("DEBUG: ChatViewModel - syncMessages SUCCESS")
                }
                .onFailure { error ->
                    println("ERROR: ChatViewModel - Failed to sync messages: ${error.message}")
                    error.printStackTrace()
                }
        }
    }

    /**
     * Observes messages from the repository and updates UI state.
     * Transforms messages into grouped format for display.
     */
    private fun observeMessages() {
        viewModelScope.launch {
            observeMessagesUseCase(conversationId)
                .catch { error ->
                    _uiState.value = ChatUiState.Error(
                        message = error.message ?: "Failed to load messages"
                    )
                }
                .collect { messages ->
                    val groupedMessages = messageGrouper.groupByDate(messages)
                    _uiState.value = ChatUiState.Success(
                        messages = groupedMessages,
                        otherParticipant = otherParticipant,
                        isConnected = true,
                        isOtherUserTyping = false,
                        currentUserId = currentUserId
                    )
                }
        }
    }

    /**
     * Sends a new message in the conversation.
     * Uses optimistic updates - message appears immediately.
     */
    private fun sendMessage(content: String) {
        if (content.isBlank()) return

        viewModelScope.launch {
            sendMessageUseCase(
                conversationId = conversationId,
                content = content.trim(),
                senderId = currentUserId
            ).onFailure { error ->
                // TODO: Show error snackbar or toast
                // For now, message stays in SENDING state, user can retry
            }
        }
    }

    /**
     * Marks a message as read by the current user.
     * Updates message status to READ.
     */
    private fun markMessageAsRead(messageId: String) {
        viewModelScope.launch {
            updateMessageStatusUseCase(messageId, MessageStatus.READ)
        }
    }

    /**
     * Deletes a message from the conversation.
     * Currently a placeholder - full implementation requires delete use case.
     */
    private fun deleteMessage(messageId: String) {
        // TODO: Implement message deletion
        // Would require a DeleteMessageUseCase
    }

    /**
     * Retries loading the conversation after an error.
     * Resets state to Loading and re-fetches data.
     */
    private fun retry() {
        _uiState.value = ChatUiState.Loading
        loadConversation()
        observeMessages()
    }

    /**
     * Marks all unread messages as read.
     * Currently a placeholder - would iterate through unread messages.
     */
    private fun markAllAsRead() {
        // TODO: Implement mark all as read
        // Would iterate through messages with isFromCurrentUser = false
        // and status != READ, then update each to READ
    }

    /**
     * Updates the typing status for the current user.
     * Sends typing indicator to other participants via WebSocket.
     */
    private fun updateTypingStatus(isTyping: Boolean) {
        // TODO: Implement typing status update
        // Would send typing status via ConversationRepository
        // to be broadcast to other participants via WebSocket
    }
}

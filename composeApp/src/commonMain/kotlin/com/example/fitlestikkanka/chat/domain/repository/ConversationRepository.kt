package com.example.fitlestikkanka.chat.domain.repository

import com.example.fitlestikkanka.chat.domain.model.Conversation
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing conversations.
 * Handles conversation metadata, participants, and typing indicators.
 */
interface ConversationRepository {
    /**
     * Gets a conversation by its ID.
     *
     * @param conversationId ID of the conversation to fetch
     * @return Result containing the Conversation on success, or error
     */
    suspend fun getConversation(conversationId: String): Result<Conversation>

    /**
     * Observes a conversation as a reactive stream.
     * Emits whenever the conversation metadata changes (e.g., last message, typing status).
     *
     * @param conversationId ID of the conversation to observe
     * @return Flow of Conversation updates
     */
    fun observeConversation(conversationId: String): Flow<Conversation>

    /**
     * Updates the typing status for the current user in a conversation.
     * This is sent to other participants via WebSocket.
     *
     * @param conversationId ID of the conversation
     * @param isTyping Whether the user is currently typing
     * @return Result indicating success or failure
     */
    suspend fun updateTypingStatus(conversationId: String, isTyping: Boolean): Result<Unit>
}

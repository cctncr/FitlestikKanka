package com.example.fitlestikkanka.chat.domain.repository

import com.example.fitlestikkanka.chat.domain.model.Message
import com.example.fitlestikkanka.chat.domain.model.MessageStatus
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing chat messages.
 * Follows the Repository pattern and Dependency Inversion principle.
 * Implementations handle both local (SQLDelight) and remote (WebSocket) data sources.
 */
interface MessageRepository {
    /**
     * Observes messages in a conversation as a reactive stream.
     * Returns a Flow that emits whenever messages are added, updated, or removed.
     *
     * @param conversationId The ID of the conversation to observe
     * @return Flow of message lists, sorted by timestamp descending
     */
    fun observeMessages(conversationId: String): Flow<List<Message>>

    /**
     * Sends a new message in a conversation.
     * Implements optimistic updates: saves locally first, then syncs with server.
     *
     * @param conversationId ID of the conversation
     * @param content Text content of the message
     * @param senderId ID of the user sending the message
     * @return Result containing the created Message on success, or error
     */
    suspend fun sendMessage(
        conversationId: String,
        content: String,
        senderId: String
    ): Result<Message>

    /**
     * Updates the status of a message (e.g., from SENT to DELIVERED or READ).
     *
     * @param messageId ID of the message to update
     * @param status New status to set
     * @return Result indicating success or failure
     */
    suspend fun updateMessageStatus(messageId: String, status: MessageStatus): Result<Unit>

    /**
     * Deletes a message from the conversation.
     *
     * @param messageId ID of the message to delete
     * @return Result indicating success or failure
     */
    suspend fun deleteMessage(messageId: String): Result<Unit>

    /**
     * Synchronizes messages with the server for a conversation.
     * Fetches any messages that exist on the server but not locally.
     *
     * @param conversationId ID of the conversation to sync
     * @return Result indicating success or failure
     */
    suspend fun syncMessages(conversationId: String): Result<Unit>
}

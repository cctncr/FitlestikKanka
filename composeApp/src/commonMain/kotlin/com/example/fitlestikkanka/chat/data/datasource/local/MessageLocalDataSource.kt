package com.example.fitlestikkanka.chat.data.datasource.local

import com.example.fitlestikkanka.chat.domain.model.Message
import com.example.fitlestikkanka.chat.domain.model.MessageStatus
import kotlinx.coroutines.flow.Flow

/**
 * Local data source interface for message persistence using SQLDelight.
 * Provides CRUD operations and reactive queries.
 *
 * Serves as the single source of truth for message data.
 * All UI updates are driven by emissions from this data source.
 */
interface MessageLocalDataSource {
    /**
     * Observes messages in a conversation as a reactive stream.
     * Emits whenever messages are inserted, updated, or deleted.
     *
     * @param conversationId ID of the conversation to observe
     * @return Flow of message lists, sorted by timestamp descending
     */
    fun observeMessages(conversationId: String): Flow<List<Message>>

    /**
     * Inserts a new message or replaces existing one (upsert).
     *
     * @param message Message to insert
     */
    suspend fun insertMessage(message: Message)

    /**
     * Inserts multiple messages in a batch operation.
     *
     * @param messages List of messages to insert
     */
    suspend fun insertMessages(messages: List<Message>)

    /**
     * Updates the status of a message.
     *
     * @param messageId ID of the message to update
     * @param status New status to set
     */
    suspend fun updateMessageStatus(messageId: String, status: MessageStatus)

    /**
     * Deletes a message from the database.
     *
     * @param messageId ID of the message to delete
     */
    suspend fun deleteMessage(messageId: String)

    /**
     * Deletes all messages in a conversation.
     *
     * @param conversationId ID of the conversation
     */
    suspend fun deleteAllMessages(conversationId: String)

    /**
     * Gets a single message by ID (non-reactive).
     *
     * @param messageId ID of the message
     * @return Message if found, null otherwise
     */
    suspend fun getMessage(messageId: String): Message?
}

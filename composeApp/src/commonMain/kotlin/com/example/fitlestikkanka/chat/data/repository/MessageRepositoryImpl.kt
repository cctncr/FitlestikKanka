package com.example.fitlestikkanka.chat.data.repository

import com.example.fitlestikkanka.chat.data.datasource.local.MessageLocalDataSource
import com.example.fitlestikkanka.chat.data.datasource.remote.WebSocketClient
import com.example.fitlestikkanka.chat.data.mapper.MessageMapper
import com.example.fitlestikkanka.chat.domain.model.Message
import com.example.fitlestikkanka.chat.domain.model.MessageStatus
import com.example.fitlestikkanka.chat.domain.repository.MessageRepository
import com.example.fitlestikkanka.core.util.TimeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Implementation of MessageRepository.
 * Coordinates between local database and remote WebSocket data sources.
 *
 * Implements offline-first architecture:
 * - All operations save to local DB first
 * - Network sync happens asynchronously
 * - UI is driven by local DB emissions
 *
 * @property localDataSource Local SQLDelight data source
 * @property webSocketClient Remote WebSocket client
 * @property currentUserId ID of the current logged-in user
 */
class MessageRepositoryImpl(
    private val localDataSource: MessageLocalDataSource,
    private val webSocketClient: WebSocketClient,
    private val currentUserId: String
) : MessageRepository {

    private val repositoryScope = CoroutineScope(Dispatchers.Default)

    init {
        // Start observing incoming messages and status updates
        observeIncomingMessages()
        observeStatusUpdates()
    }

    override fun observeMessages(conversationId: String): Flow<List<Message>> {
        return localDataSource.observeMessages(conversationId)
            .map { messages ->
                messages.map { message ->
                    message.copy(isFromCurrentUser = message.senderId == currentUserId)
                }
            }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun sendMessage(
        conversationId: String,
        content: String,
        senderId: String
    ): Result<Message> {
        return try {
            // Create message with SENDING status
            val message = Message(
                id = Uuid.random().toString(),
                conversationId = conversationId,
                content = content,
                senderId = senderId,
                timestamp = TimeUtils.now(),
                status = MessageStatus.SENDING,
                isFromCurrentUser = true
            )

            // Save to local DB first (optimistic update)
            localDataSource.insertMessage(message)

            // Send to server via WebSocket
            val dto = MessageMapper.toDto(message)
            webSocketClient.sendMessage(dto)
                .onSuccess {
                    // Update status to SENT on successful send
                    localDataSource.updateMessageStatus(message.id, MessageStatus.SENT)
                }
                .onFailure {
                    // Keep message in SENDING state, user can retry
                    // Could show error toast/snackbar
                }

            Result.success(message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateMessageStatus(
        messageId: String,
        status: MessageStatus
    ): Result<Unit> {
        return try {
            localDataSource.updateMessageStatus(messageId, status)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteMessage(messageId: String): Result<Unit> {
        return try {
            localDataSource.deleteMessage(messageId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun syncMessages(conversationId: String): Result<Unit> {
        // TODO: Implement full message sync from server
        // Would fetch historical messages not present locally
        return Result.success(Unit)
    }

    /**
     * Observes incoming messages from WebSocket and saves them to local DB.
     */
    private fun observeIncomingMessages() {
        repositoryScope.launch {
            webSocketClient.observeIncomingMessages()
                .map { dto -> MessageMapper.toDomain(dto, currentUserId) }
                .collect { message ->
                    localDataSource.insertMessage(message)
                }
        }
    }

    /**
     * Observes message status updates from WebSocket and updates local DB.
     */
    private fun observeStatusUpdates() {
        repositoryScope.launch {
            webSocketClient.observeMessageStatusUpdates()
                .collect { update ->
                    val status = MessageStatus.valueOf(update.status)
                    localDataSource.updateMessageStatus(update.messageId, status)
                }
        }
    }
}

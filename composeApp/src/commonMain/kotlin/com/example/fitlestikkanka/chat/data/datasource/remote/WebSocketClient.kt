package com.example.fitlestikkanka.chat.data.datasource.remote

import com.example.fitlestikkanka.chat.data.datasource.remote.dto.MessageDto
import com.example.fitlestikkanka.chat.data.datasource.remote.dto.MessageStatusUpdateDto
import kotlinx.coroutines.flow.Flow

/**
 * WebSocket client interface for real-time messaging.
 * Handles bidirectional communication with the chat server.
 *
 * Implementations should:
 * - Maintain persistent WebSocket connection
 * - Handle automatic reconnection
 * - Provide Flow-based reactive streams for incoming data
 */
interface WebSocketClient {
    /**
     * Establishes WebSocket connection for a conversation.
     *
     * @param conversationId ID of the conversation to connect to
     * @return Result indicating success or connection error
     */
    suspend fun connect(conversationId: String): Result<Unit>

    /**
     * Closes the WebSocket connection gracefully.
     */
    suspend fun disconnect()

    /**
     * Sends a message to the server via WebSocket.
     *
     * @param message Message DTO to send
     * @return Result indicating success or send failure
     */
    suspend fun sendMessage(message: MessageDto): Result<Unit>

    /**
     * Sends a message status update to the server.
     *
     * @param update Status update DTO
     * @return Result indicating success or failure
     */
    suspend fun updateMessageStatus(update: MessageStatusUpdateDto): Result<Unit>

    /**
     * Observes incoming messages from the server as a reactive stream.
     *
     * @return Flow of MessageDto objects received from server
     */
    fun observeIncomingMessages(): Flow<MessageDto>

    /**
     * Observes message status updates from the server.
     *
     * @return Flow of status update DTOs
     */
    fun observeMessageStatusUpdates(): Flow<MessageStatusUpdateDto>

    /**
     * Observes WebSocket connection state changes.
     *
     * @return Flow of ConnectionState updates
     */
    fun observeConnectionState(): Flow<ConnectionState>
}

/**
 * Represents the WebSocket connection state.
 */
enum class ConnectionState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    ERROR
}

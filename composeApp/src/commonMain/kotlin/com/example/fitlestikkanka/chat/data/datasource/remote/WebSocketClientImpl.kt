package com.example.fitlestikkanka.chat.data.datasource.remote

import com.example.fitlestikkanka.auth.domain.repository.AuthRepository
import com.example.fitlestikkanka.chat.data.datasource.remote.dto.MessageDto
import com.example.fitlestikkanka.chat.data.datasource.remote.dto.MessageStatusUpdateDto
import com.example.fitlestikkanka.core.config.ApiConfig
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

/**
 * Implementation of WebSocketClient using Ktor WebSocket client.
 * Handles real-time bidirectional communication with chat server.
 *
 * Connects to FastAPI backend at ws://localhost:8000/ws/{token}
 * Automatically handles incoming messages and status updates.
 *
 * @property httpClient Ktor HttpClient with WebSocket support
 * @property authRepository Auth repository for token retrieval
 */
class WebSocketClientImpl(
    private val httpClient: HttpClient,
    private val authRepository: AuthRepository
) : WebSocketClient {

    private val json = Json { ignoreUnknownKeys = true }

    private val _incomingMessages = MutableSharedFlow<MessageDto>()
    private val _statusUpdates = MutableSharedFlow<MessageStatusUpdateDto>()
    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)

    private var session: DefaultClientWebSocketSession? = null
    private var messageListenerJob: Job? = null

    override suspend fun connect(conversationId: String): Result<Unit> {
        return try {
            _connectionState.value = ConnectionState.CONNECTING

            // Get authentication token
            val token = authRepository.getStoredToken()?.accessToken
                ?: return Result.failure(Exception("No authentication token available"))

            // Establish WebSocket connection with token
            session = httpClient.webSocketSession(
                urlString = "${ApiConfig.WS_BASE_URL}/ws/$token"
            )

            _connectionState.value = ConnectionState.CONNECTED

            // Start listening for incoming messages in background
            messageListenerJob = CoroutineScope(Dispatchers.Default).launch {
                listenForMessages()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            _connectionState.value = ConnectionState.ERROR
            Result.failure(e)
        }
    }

    override suspend fun disconnect() {
        messageListenerJob?.cancel()
        session?.close()
        session = null
        _connectionState.value = ConnectionState.DISCONNECTED
    }

    override suspend fun sendMessage(message: MessageDto): Result<Unit> {
        return try {
            val messageJson = json.encodeToString(message)
            session?.send(Frame.Text(messageJson))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateMessageStatus(update: MessageStatusUpdateDto): Result<Unit> {
        return try {
            val updateJson = json.encodeToString(update)
            session?.send(Frame.Text(updateJson))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun observeIncomingMessages(): Flow<MessageDto> {
        return _incomingMessages.asSharedFlow()
    }

    override fun observeMessageStatusUpdates(): Flow<MessageStatusUpdateDto> {
        return _statusUpdates.asSharedFlow()
    }

    override fun observeConnectionState(): Flow<ConnectionState> {
        return _connectionState.asStateFlow()
    }

    /**
     * Listen for incoming WebSocket frames.
     *
     * Parses JSON and routes to appropriate flow based on message type.
     * Handles both MessageDto (incoming messages) and MessageStatusUpdateDto (status updates).
     */
    private suspend fun listenForMessages() {
        try {
            val incoming = session?.incoming ?: return
            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val text = frame.readText()
                        try {
                            // Try parsing as message first
                            val message = json.decodeFromString<MessageDto>(text)
                            _incomingMessages.emit(message)
                        } catch (e: SerializationException) {
                            // If not a message, try parsing as status update
                            try {
                                val statusUpdate = json.decodeFromString<MessageStatusUpdateDto>(text)
                                _statusUpdates.emit(statusUpdate)
                            } catch (e2: SerializationException) {
                                // Unknown message format, log and ignore
                                println("Unknown WebSocket message format: $text")
                            }
                        }
                    }
                    else -> {
                        // Ignore other frame types (Binary, Close, etc.)
                    }
                }
            }
        } catch (e: Exception) {
            _connectionState.value = ConnectionState.ERROR
            println("WebSocket listener error: ${e.message}")
        }
    }
}

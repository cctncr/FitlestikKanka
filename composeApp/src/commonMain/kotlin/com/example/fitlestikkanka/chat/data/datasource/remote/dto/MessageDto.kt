package com.example.fitlestikkanka.chat.data.datasource.remote.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object for Message entity.
 * Used for network communication via WebSocket.
 *
 * Separate from domain model to:
 * - Keep domain layer clean
 * - Allow network format changes without affecting domain
 * - Add serialization annotations without polluting domain
 */
@Serializable
data class MessageDto(
    val id: String,
    val conversationId: String,
    val content: String,
    val senderId: String,
    val timestamp: Long, // Epoch milliseconds
    val status: String // Serialized enum name
)

/**
 * Data Transfer Object for message status updates.
 * Used when WebSocket server notifies about status changes.
 */
@Serializable
data class MessageStatusUpdateDto(
    val messageId: String,
    val status: String, // SENT, DELIVERED, READ
    val timestamp: Long
)

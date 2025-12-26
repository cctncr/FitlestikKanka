package com.example.fitlestikkanka.chat.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

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
    @SerialName("conversation_id")
    val conversationId: String,
    val content: String,
    @SerialName("sender_id")
    val senderId: String,
    val timestamp: Long, // Epoch milliseconds
    val status: String, // Serialized enum name
    @SerialName("ai_analysis")
    val aiAnalysis: AiAnalysisDto? = null  // NEW: AI classification result
)

/**
 * DTO for AI classification result from backend.
 *
 * Contains classification type and extracted data.
 */
@Serializable
data class AiAnalysisDto(
    val classification: String,  // "TASK", "EXPENSE", "NORMAL"
    @SerialName("extracted_data")
    val extractedData: JsonObject? = null  // Flexible JSON for task/expense details
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

package com.example.fitlestikkanka.chat.domain.model

import kotlinx.datetime.Instant

/**
 * Domain entity representing a text message in a conversation.
 * This is the core business entity used throughout the app.
 *
 * @property id Unique identifier for the message
 * @property conversationId ID of the conversation this message belongs to
 * @property content The text content of the message
 * @property senderId ID of the user who sent this message
 * @property timestamp When the message was created (epoch milliseconds)
 * @property status Current delivery/read status of the message
 * @property isFromCurrentUser Whether this message was sent by the current user
 */
data class Message(
    val id: String,
    val conversationId: String,
    val content: String,
    val senderId: String,
    val timestamp: Instant,
    val status: MessageStatus,
    val isFromCurrentUser: Boolean
)

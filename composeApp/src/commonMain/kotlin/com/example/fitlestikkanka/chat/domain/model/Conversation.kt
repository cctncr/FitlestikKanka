package com.example.fitlestikkanka.chat.domain.model

import kotlinx.datetime.Instant

/**
 * Domain entity representing a 1-to-1 conversation between two users.
 *
 * @property id Unique identifier for the conversation
 * @property participants List of users participating in this conversation (always 2 for 1-to-1)
 * @property lastMessage The most recent message in the conversation
 * @property lastMessageTimestamp Timestamp of the last message
 * @property unreadCount Number of unread messages in this conversation
 */
data class Conversation(
    val id: String,
    val participants: List<Participant>,
    val lastMessage: Message? = null,
    val lastMessageTimestamp: Instant? = null,
    val unreadCount: Int = 0
) {
    /**
     * Gets the other participant in the conversation (not the current user)
     */
    val otherParticipant: Participant?
        get() = participants.firstOrNull { !it.isCurrentUser }
}

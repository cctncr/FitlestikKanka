package com.example.fitlestikkanka.chat.domain.model

/**
 * Represents the delivery and read status of a message in the chat.
 * Follows WhatsApp-like status progression: SENDING → SENT → DELIVERED → READ
 */
enum class MessageStatus {
    /**
     * Message is being sent to the server (no checkmark shown)
     */
    SENDING,

    /**
     * Message has been sent to server successfully (1 checkmark)
     */
    SENT,

    /**
     * Message has been delivered to recipient's device (2 gray checkmarks)
     */
    DELIVERED,

    /**
     * Message has been read by recipient (2 blue checkmarks)
     */
    READ;

    /**
     * Number of checkmarks to display for this status
     */
    val checkmarkCount: Int
        get() = when (this) {
            SENDING -> 0
            SENT -> 1
            DELIVERED, READ -> 2
        }

    /**
     * Whether this message has been read by the recipient
     */
    val isRead: Boolean
        get() = this == READ
}

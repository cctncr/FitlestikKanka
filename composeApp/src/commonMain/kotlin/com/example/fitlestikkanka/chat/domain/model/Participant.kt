package com.example.fitlestikkanka.chat.domain.model

/**
 * Represents a participant in a conversation.
 * Contains user information and their current state in the chat.
 *
 * @property userId Unique identifier for the user
 * @property name Display name of the user
 * @property avatarUrl Optional URL to user's avatar image
 * @property isOnline Whether the user is currently online
 * @property isCurrentUser Whether this participant is the current logged-in user
 * @property isTyping Whether this user is currently typing a message
 */
data class Participant(
    val userId: String,
    val name: String,
    val avatarUrl: String? = null,
    val isOnline: Boolean = false,
    val isCurrentUser: Boolean = false,
    val isTyping: Boolean = false
)

package com.example.fitlestikkanka.chat.domain.model

/**
 * Domain entity representing a user in the application.
 * This is a simplified user model focused on chat functionality.
 *
 * @property id Unique identifier for the user
 * @property name Display name of the user
 * @property avatarUrl Optional URL to user's avatar image
 * @property isOnline Whether the user is currently online
 */
data class User(
    val id: String,
    val name: String,
    val avatarUrl: String? = null,
    val isOnline: Boolean = false
)

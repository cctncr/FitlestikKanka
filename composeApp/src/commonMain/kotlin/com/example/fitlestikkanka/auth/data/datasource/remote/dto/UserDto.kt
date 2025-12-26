package com.example.fitlestikkanka.auth.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for user data from backend.
 *
 * Received from GET /api/auth/me endpoint.
 */
@Serializable
data class UserDto(
    val id: Int,
    val username: String,
    val email: String,
    @SerialName("created_at")
    val createdAt: String  // ISO timestamp string
)

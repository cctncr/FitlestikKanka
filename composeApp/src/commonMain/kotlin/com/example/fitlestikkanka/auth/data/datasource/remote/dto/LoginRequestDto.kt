package com.example.fitlestikkanka.auth.data.datasource.remote.dto

import kotlinx.serialization.Serializable

/**
 * DTO for login request.
 *
 * Sent to POST /api/auth/login endpoint.
 */
@Serializable
data class LoginRequestDto(
    val username: String,
    val password: String
)

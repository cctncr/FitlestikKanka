package com.example.fitlestikkanka.auth.domain.model

import kotlinx.datetime.Instant

/**
 * Domain entity representing an authenticated user.
 *
 * Matches backend User model from FastAPI.
 */
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val createdAt: Instant
)

package com.example.fitlestikkanka.auth.domain.model

/**
 * Domain entity representing authentication token.
 *
 * JWT token received from backend after successful login.
 */
data class AuthToken(
    val accessToken: String,
    val tokenType: String = "bearer"
)

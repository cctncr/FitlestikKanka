package com.example.fitlestikkanka.auth.domain.repository

import com.example.fitlestikkanka.auth.domain.model.AuthToken
import com.example.fitlestikkanka.auth.domain.model.User

/**
 * Repository interface for authentication operations.
 *
 * Handles login, token storage, and user retrieval.
 */
interface AuthRepository {
    /**
     * Authenticate user with username and password.
     *
     * @param username User's username
     * @param password User's password
     * @return Result containing User on success, error on failure
     */
    suspend fun login(username: String, password: String): Result<User>

    /**
     * Get currently authenticated user.
     *
     * @return Result containing User if authenticated, error if not
     */
    suspend fun getCurrentUser(): Result<User>

    /**
     * Get stored authentication token.
     *
     * @return AuthToken if available, null otherwise
     */
    suspend fun getStoredToken(): AuthToken?

    /**
     * Clear stored authentication data.
     */
    suspend fun logout()
}

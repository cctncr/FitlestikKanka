package com.example.fitlestikkanka.auth.data.datasource.local

import com.example.fitlestikkanka.auth.domain.model.AuthToken
import com.example.fitlestikkanka.auth.domain.model.User

/**
 * Local data source interface for authentication data.
 *
 * Platform-specific implementations handle token storage
 * (SharedPreferences on Android, UserDefaults on iOS, etc.)
 */
interface AuthLocalDataSource {
    /**
     * Save authentication token.
     *
     * @param token AuthToken to persist
     */
    suspend fun saveToken(token: AuthToken)

    /**
     * Get stored authentication token.
     *
     * @return AuthToken if available, null otherwise
     */
    suspend fun getToken(): AuthToken?

    /**
     * Clear stored token.
     */
    suspend fun clearToken()

    /**
     * Save current user data.
     *
     * @param user User to persist
     */
    suspend fun saveCurrentUser(user: User)

    /**
     * Get stored current user.
     *
     * @return User if available, null otherwise
     */
    suspend fun getCurrentUser(): User?

    /**
     * Clear current user data.
     */
    suspend fun clearCurrentUser()
}

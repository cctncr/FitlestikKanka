package com.example.fitlestikkanka.auth.data.datasource.remote

import com.example.fitlestikkanka.auth.data.datasource.remote.dto.LoginResponseDto
import com.example.fitlestikkanka.auth.data.datasource.remote.dto.UserDto

/**
 * API service interface for authentication endpoints.
 *
 * Defines backend authentication operations.
 */
interface AuthApiService {
    /**
     * Login user with credentials.
     *
     * @param username User's username
     * @param password User's password
     * @return Result containing LoginResponseDto with token
     */
    suspend fun login(username: String, password: String): Result<LoginResponseDto>

    /**
     * Get current authenticated user.
     *
     * @param token Bearer token
     * @return Result containing UserDto
     */
    suspend fun getCurrentUser(token: String): Result<UserDto>
}

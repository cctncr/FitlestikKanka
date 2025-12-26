package com.example.fitlestikkanka.auth.data.repository

import com.example.fitlestikkanka.auth.data.datasource.local.AuthLocalDataSource
import com.example.fitlestikkanka.auth.data.datasource.remote.AuthApiService
import com.example.fitlestikkanka.auth.data.mapper.AuthMapper
import com.example.fitlestikkanka.auth.domain.model.AuthToken
import com.example.fitlestikkanka.auth.domain.model.User
import com.example.fitlestikkanka.auth.domain.repository.AuthRepository

/**
 * Implementation of AuthRepository.
 *
 * Coordinates between remote API and local storage.
 */
class AuthRepositoryImpl(
    private val apiService: AuthApiService,
    private val localDataSource: AuthLocalDataSource
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<User> {
        return apiService.login(username, password)
            .mapCatching { loginResponseDto ->
                // Map to domain model
                val token = AuthMapper.loginResponseToDomain(loginResponseDto)

                // Save token locally
                localDataSource.saveToken(token)

                // Fetch user data
                val userDto = apiService.getCurrentUser(token.accessToken).getOrThrow()
                val user = AuthMapper.userDtoToDomain(userDto)

                // Save user locally
                localDataSource.saveCurrentUser(user)

                user
            }
    }

    override suspend fun getCurrentUser(): Result<User> {
        // Check local storage first
        val localUser = localDataSource.getCurrentUser()
        if (localUser != null) {
            return Result.success(localUser)
        }

        // If not available locally, try to fetch from API
        val token = localDataSource.getToken()
            ?: return Result.failure(Exception("No stored token"))

        return apiService.getCurrentUser(token.accessToken)
            .mapCatching { userDto ->
                val user = AuthMapper.userDtoToDomain(userDto)
                localDataSource.saveCurrentUser(user)
                user
            }
    }

    override suspend fun getStoredToken(): AuthToken? {
        return localDataSource.getToken()
    }

    override suspend fun logout() {
        localDataSource.clearToken()
        localDataSource.clearCurrentUser()
    }
}

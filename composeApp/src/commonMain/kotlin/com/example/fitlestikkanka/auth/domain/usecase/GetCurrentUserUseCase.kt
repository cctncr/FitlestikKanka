package com.example.fitlestikkanka.auth.domain.usecase

import com.example.fitlestikkanka.auth.domain.model.User
import com.example.fitlestikkanka.auth.domain.repository.AuthRepository

/**
 * Use case for retrieving currently authenticated user.
 *
 * Used to check if user is already logged in on app startup.
 */
class GetCurrentUserUseCase(
    private val authRepository: AuthRepository
) {
    /**
     * Get current authenticated user.
     *
     * @return Result containing User if authenticated, error otherwise
     */
    suspend operator fun invoke(): Result<User> {
        return authRepository.getCurrentUser()
    }
}

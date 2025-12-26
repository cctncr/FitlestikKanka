package com.example.fitlestikkanka.auth.domain.usecase

import com.example.fitlestikkanka.auth.domain.model.User
import com.example.fitlestikkanka.auth.domain.repository.AuthRepository

/**
 * Use case for user authentication.
 *
 * Encapsulates the business logic of logging in a user.
 */
class LoginUseCase(
    private val authRepository: AuthRepository
) {
    /**
     * Execute login operation.
     *
     * @param username User's username
     * @param password User's password
     * @return Result containing authenticated User on success
     */
    suspend operator fun invoke(username: String, password: String): Result<User> {
        return authRepository.login(username, password)
    }
}

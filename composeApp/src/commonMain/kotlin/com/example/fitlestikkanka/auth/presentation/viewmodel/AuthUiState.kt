package com.example.fitlestikkanka.auth.presentation.viewmodel

import com.example.fitlestikkanka.auth.domain.model.User

/**
 * UI state for authentication flow.
 *
 * Sealed interface representing all possible authentication states.
 */
sealed interface AuthUiState {
    /**
     * User is not authenticated - show login screen
     */
    data object Unauthenticated : AuthUiState

    /**
     * Authentication in progress - show loading indicator
     */
    data object Loading : AuthUiState

    /**
     * User is authenticated - show main app content
     *
     * @param user Authenticated user
     */
    data class Authenticated(val user: User) : AuthUiState

    /**
     * Authentication error - show error message
     *
     * @param message Error message to display
     */
    data class Error(val message: String) : AuthUiState
}

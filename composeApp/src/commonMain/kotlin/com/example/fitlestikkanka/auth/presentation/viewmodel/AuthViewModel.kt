package com.example.fitlestikkanka.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlestikkanka.auth.domain.usecase.GetCurrentUserUseCase
import com.example.fitlestikkanka.auth.domain.usecase.LoginUseCase
import com.example.fitlestikkanka.chat.data.datasource.remote.WebSocketClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for authentication flow.
 *
 * Manages authentication state and coordinates login/logout operations.
 * Auto-connects WebSocket on successful authentication.
 */
class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val webSocketClient: WebSocketClient
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Loading)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        checkExistingAuth()
    }

    /**
     * Check if user is already authenticated on startup.
     */
    private fun checkExistingAuth() {
        viewModelScope.launch {
            getCurrentUserUseCase()
                .onSuccess { user ->
                    _uiState.value = AuthUiState.Authenticated(user)
                    connectWebSocket()
                }
                .onFailure {
                    _uiState.value = AuthUiState.Unauthenticated
                }
        }
    }

    /**
     * Login with username and password.
     *
     * @param username User's username
     * @param password User's password
     */
    fun login(username: String, password: String) {
        _uiState.value = AuthUiState.Loading

        viewModelScope.launch {
            loginUseCase(username, password)
                .onSuccess { user ->
                    _uiState.value = AuthUiState.Authenticated(user)
                    connectWebSocket()
                }
                .onFailure { error ->
                    _uiState.value = AuthUiState.Error(
                        message = error.message ?: "Login failed. Please try again."
                    )
                }
        }
    }

    /**
     * Retry after error.
     */
    fun retry() {
        _uiState.value = AuthUiState.Unauthenticated
    }

    /**
     * Auto-connect WebSocket after successful authentication.
     */
    private suspend fun connectWebSocket() {
        // Use fixed conversation ID for now
        // TODO: Get actual conversation ID from backend if needed
        webSocketClient.connect("main-conversation")
            .onFailure { error ->
                println("WebSocket connection failed: ${error.message}")
                // Don't fail auth if WebSocket fails - user can still use app
            }
    }
}

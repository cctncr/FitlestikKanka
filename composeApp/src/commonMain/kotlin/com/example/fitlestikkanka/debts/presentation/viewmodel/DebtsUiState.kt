package com.example.fitlestikkanka.debts.presentation.viewmodel

import com.example.fitlestikkanka.debts.domain.model.Debt

/**
 * UI state for Debts screen.
 *
 * Sealed interface representing all possible UI states.
 * Enables exhaustive when expressions and type-safe state management.
 */
sealed interface DebtsUiState {
    /**
     * Loading state - fetching debts from backend
     */
    data object Loading : DebtsUiState

    /**
     * Success state - debts loaded successfully
     *
     * @param debts List of debts to display
     */
    data class Success(
        val debts: List<Debt>
    ) : DebtsUiState

    /**
     * Empty state - no debts available
     */
    data object Empty : DebtsUiState

    /**
     * Error state - failed to load debts
     *
     * @param message Error message to display
     */
    data class Error(
        val message: String
    ) : DebtsUiState
}

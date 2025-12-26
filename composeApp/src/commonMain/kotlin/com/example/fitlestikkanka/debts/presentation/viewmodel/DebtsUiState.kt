package com.example.fitlestikkanka.debts.presentation.viewmodel

import com.example.fitlestikkanka.debts.domain.model.DebtBalance

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
     * @param balances List of debt balances to display
     */
    data class Success(
        val balances: List<DebtBalance>
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

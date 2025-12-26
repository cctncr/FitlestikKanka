package com.example.fitlestikkanka.debts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlestikkanka.debts.domain.repository.DebtsRepository
import com.example.fitlestikkanka.debts.domain.usecase.LoadDebtBalanceUseCase
import com.example.fitlestikkanka.debts.domain.usecase.SettleDebtUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Debts screen.
 *
 * Manages UI state and coordinates data loading with debt operations.
 * Auto-refreshes when debts change (e.g., from AI classification).
 * Follows MVVM pattern with unidirectional data flow.
 */
class DebtsViewModel(
    private val loadDebtBalanceUseCase: LoadDebtBalanceUseCase,
    private val settleDebtUseCase: SettleDebtUseCase,
    private val debtsRepository: DebtsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DebtsUiState>(DebtsUiState.Loading)
    val uiState: StateFlow<DebtsUiState> = _uiState.asStateFlow()

    init {
        loadDebts()
        observeDebtsChanges()
    }

    /**
     * Loads debt balances from backend.
     */
    fun loadDebts() {
        _uiState.value = DebtsUiState.Loading

        viewModelScope.launch {
            loadDebtBalanceUseCase()
                .onSuccess { balances ->
                    _uiState.value = if (balances.isEmpty()) {
                        DebtsUiState.Empty
                    } else {
                        DebtsUiState.Success(balances = balances)
                    }
                }
                .onFailure { error ->
                    _uiState.value = DebtsUiState.Error(
                        message = error.message ?: "Failed to load debts"
                    )
                }
        }
    }

    /**
     * Auto-refresh debts when repository emits changes.
     *
     * This handles updates from AI classification.
     */
    private fun observeDebtsChanges() {
        viewModelScope.launch {
            debtsRepository.observeBalance()
                .collect { balances ->
                    if (balances.isNotEmpty() || _uiState.value is DebtsUiState.Success) {
                        _uiState.value = DebtsUiState.Success(balances = balances)
                    }
                }
        }
    }

    /**
     * Settle debt with a user.
     *
     * @param userId User ID to settle with
     * @param amount Amount to settle
     */
    fun settleDebt(userId: Int, amount: Double) {
        viewModelScope.launch {
            settleDebtUseCase(userId, amount)
                .onSuccess {
                    // Repository will emit updated balance via observeBalance()
                }
                .onFailure { error ->
                    // Could show toast/snackbar with error
                    println("Failed to settle debt: ${error.message}")
                }
        }
    }

    /**
     * Retries loading debts after an error.
     */
    fun retry() {
        loadDebts()
    }
}

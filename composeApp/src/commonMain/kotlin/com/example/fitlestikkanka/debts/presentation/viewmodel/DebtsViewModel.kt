package com.example.fitlestikkanka.debts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlestikkanka.debts.domain.usecase.LoadDebtsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Debts screen.
 *
 * Manages UI state and coordinates data loading.
 * Follows MVVM pattern with unidirectional data flow.
 */
class DebtsViewModel(
    private val loadDebtsUseCase: LoadDebtsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DebtsUiState>(DebtsUiState.Loading)
    val uiState: StateFlow<DebtsUiState> = _uiState.asStateFlow()

    init {
        loadDebts()
    }

    /**
     * Loads debts from backend.
     */
    fun loadDebts() {
        _uiState.value = DebtsUiState.Loading

        viewModelScope.launch {
            loadDebtsUseCase()
                .onSuccess { debts ->
                    _uiState.value = if (debts.isEmpty()) {
                        DebtsUiState.Empty
                    } else {
                        DebtsUiState.Success(debts = debts)
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
     * Retries loading debts after an error.
     */
    fun retry() {
        loadDebts()
    }
}

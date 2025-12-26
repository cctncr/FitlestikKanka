package com.example.fitlestikkanka.tasks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlestikkanka.tasks.domain.usecase.LoadTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Tasks screen.
 *
 * Manages UI state and coordinates data loading.
 * Follows MVVM pattern with unidirectional data flow.
 */
class TasksViewModel(
    private val loadTasksUseCase: LoadTasksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<TasksUiState>(TasksUiState.Loading)
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    /**
     * Loads tasks from backend.
     */
    fun loadTasks() {
        _uiState.value = TasksUiState.Loading

        viewModelScope.launch {
            loadTasksUseCase()
                .onSuccess { tasks ->
                    _uiState.value = if (tasks.isEmpty()) {
                        TasksUiState.Empty
                    } else {
                        TasksUiState.Success(tasks = tasks)
                    }
                }
                .onFailure { error ->
                    _uiState.value = TasksUiState.Error(
                        message = error.message ?: "Failed to load tasks"
                    )
                }
        }
    }

    /**
     * Retries loading tasks after an error.
     */
    fun retry() {
        loadTasks()
    }
}

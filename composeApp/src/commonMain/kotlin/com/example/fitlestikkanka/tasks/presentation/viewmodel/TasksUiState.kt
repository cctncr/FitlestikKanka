package com.example.fitlestikkanka.tasks.presentation.viewmodel

import com.example.fitlestikkanka.tasks.domain.model.Task

/**
 * UI state for Tasks screen.
 *
 * Sealed interface representing all possible UI states.
 * Enables exhaustive when expressions and type-safe state management.
 */
sealed interface TasksUiState {
    /**
     * Loading state - fetching tasks from backend
     */
    data object Loading : TasksUiState

    /**
     * Success state - tasks loaded successfully
     *
     * @param tasks List of tasks to display
     */
    data class Success(
        val tasks: List<Task>
    ) : TasksUiState

    /**
     * Empty state - no tasks available
     */
    data object Empty : TasksUiState

    /**
     * Error state - failed to load tasks
     *
     * @param message Error message to display
     */
    data class Error(
        val message: String
    ) : TasksUiState
}

package com.example.fitlestikkanka.tasks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitlestikkanka.tasks.domain.model.TaskStatus
import com.example.fitlestikkanka.tasks.domain.repository.TasksRepository
import com.example.fitlestikkanka.tasks.domain.usecase.DeleteTaskUseCase
import com.example.fitlestikkanka.tasks.domain.usecase.LoadTasksUseCase
import com.example.fitlestikkanka.tasks.domain.usecase.UpdateTaskStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Tasks screen.
 *
 * Manages UI state and coordinates data loading with CRUD operations.
 * Auto-refreshes when tasks change (e.g., from AI classification).
 * Follows MVVM pattern with unidirectional data flow.
 */
class TasksViewModel(
    private val loadTasksUseCase: LoadTasksUseCase,
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val tasksRepository: TasksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TasksUiState>(TasksUiState.Loading)
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
        observeTasksChanges()
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
     * Auto-refresh tasks when repository emits changes.
     *
     * This handles updates from AI classification.
     */
    private fun observeTasksChanges() {
        viewModelScope.launch {
            tasksRepository.observeTasks()
                .collect { tasks ->
                    if (tasks.isNotEmpty() || _uiState.value is TasksUiState.Success) {
                        _uiState.value = TasksUiState.Success(tasks = tasks)
                    }
                }
        }
    }

    /**
     * Update task status (e.g., mark as completed).
     *
     * @param taskId Task ID to update
     * @param status New status
     */
    fun updateTaskStatus(taskId: Int, status: TaskStatus) {
        viewModelScope.launch {
            updateTaskStatusUseCase(taskId, status)
                .onSuccess {
                    // Repository will emit updated list via observeTasks()
                }
                .onFailure { error ->
                    // Could show toast/snackbar with error
                    println("Failed to update task: ${error.message}")
                }
        }
    }

    /**
     * Delete a task.
     *
     * @param taskId Task ID to delete
     */
    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            deleteTaskUseCase(taskId)
                .onSuccess {
                    // Repository will emit updated list via observeTasks()
                }
                .onFailure { error ->
                    // Could show toast/snackbar with error
                    println("Failed to delete task: ${error.message}")
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

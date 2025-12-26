package com.example.fitlestikkanka.tasks.domain.repository

import com.example.fitlestikkanka.tasks.domain.model.Task
import com.example.fitlestikkanka.tasks.domain.model.TaskStatus
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for tasks data operations.
 *
 * Provides abstraction over data sources (remote API).
 */
interface TasksRepository {
    /**
     * Load tasks from backend with optional status filter.
     *
     * @param status Optional status filter
     * @return Result containing list of tasks or error
     */
    suspend fun loadTasks(status: TaskStatus? = null): Result<List<Task>>

    /**
     * Update task status.
     *
     * @param taskId Task ID to update
     * @param status New status
     * @return Result containing updated task
     */
    suspend fun updateTaskStatus(taskId: Int, status: TaskStatus): Result<Task>

    /**
     * Delete a task.
     *
     * @param taskId Task ID to delete
     * @return Result indicating success/failure
     */
    suspend fun deleteTask(taskId: Int): Result<Unit>

    /**
     * Observe tasks list for auto-refresh.
     *
     * Emits updated list when tasks change (e.g., from AI classification).
     *
     * @return Flow of task lists
     */
    fun observeTasks(): Flow<List<Task>>
}

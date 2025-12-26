package com.example.fitlestikkanka.tasks.data.datasource.remote

import com.example.fitlestikkanka.tasks.data.datasource.remote.dto.TaskDto
import com.example.fitlestikkanka.tasks.domain.model.TaskStatus

/**
 * API service interface for tasks endpoints.
 *
 * Defines backend tasks CRUD operations.
 */
interface TasksApiService {
    /**
     * Get tasks from backend with optional status filter.
     *
     * @param status Optional status filter (pending, in_progress, completed, cancelled)
     * @return Result containing list of TaskDto
     */
    suspend fun getTasks(status: String? = null): Result<List<TaskDto>>

    /**
     * Update task status.
     *
     * @param taskId Task ID to update
     * @param status New status
     * @return Result containing updated TaskDto
     */
    suspend fun updateTask(taskId: Int, status: TaskStatus): Result<TaskDto>

    /**
     * Delete a task.
     *
     * @param taskId Task ID to delete
     * @return Result indicating success/failure
     */
    suspend fun deleteTask(taskId: Int): Result<Unit>
}

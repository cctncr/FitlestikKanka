package com.example.fitlestikkanka.tasks.domain.repository

import com.example.fitlestikkanka.tasks.domain.model.Task

/**
 * Repository interface for tasks data operations.
 *
 * Follows Clean Architecture principles - domain layer defines contracts,
 * data layer provides implementations.
 */
interface TasksRepository {
    /**
     * Loads all tasks from backend.
     *
     * @return Result containing list of tasks or error
     */
    suspend fun loadTasks(): Result<List<Task>>
}

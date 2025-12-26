package com.example.fitlestikkanka.tasks.domain.usecase

import com.example.fitlestikkanka.tasks.domain.model.Task
import com.example.fitlestikkanka.tasks.domain.model.TaskStatus
import com.example.fitlestikkanka.tasks.domain.repository.TasksRepository

/**
 * Use case for loading tasks.
 *
 * Encapsulates the business logic of fetching tasks from backend.
 */
class LoadTasksUseCase(
    private val tasksRepository: TasksRepository
) {
    /**
     * Execute load tasks operation.
     *
     * @param status Optional status filter
     * @return Result containing list of tasks on success
     */
    suspend operator fun invoke(status: TaskStatus? = null): Result<List<Task>> {
        return tasksRepository.loadTasks(status)
    }
}

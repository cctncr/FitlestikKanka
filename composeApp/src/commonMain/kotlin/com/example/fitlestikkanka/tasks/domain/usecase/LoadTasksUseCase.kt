package com.example.fitlestikkanka.tasks.domain.usecase

import com.example.fitlestikkanka.tasks.domain.model.Task
import com.example.fitlestikkanka.tasks.domain.repository.TasksRepository

/**
 * Use case for loading tasks.
 *
 * Encapsulates business logic for fetching task list.
 * Single responsibility: coordinate task loading operation.
 */
class LoadTasksUseCase(
    private val tasksRepository: TasksRepository
) {
    /**
     * Executes the use case to load tasks.
     *
     * @return Result containing list of tasks or error
     */
    suspend operator fun invoke(): Result<List<Task>> {
        return tasksRepository.loadTasks()
    }
}

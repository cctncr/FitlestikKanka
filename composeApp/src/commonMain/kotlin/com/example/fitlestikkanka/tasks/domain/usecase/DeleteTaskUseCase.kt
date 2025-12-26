package com.example.fitlestikkanka.tasks.domain.usecase

import com.example.fitlestikkanka.tasks.domain.repository.TasksRepository

/**
 * Use case for deleting a task.
 *
 * Encapsulates the business logic of removing a task.
 */
class DeleteTaskUseCase(
    private val tasksRepository: TasksRepository
) {
    /**
     * Execute delete task operation.
     *
     * @param taskId Task ID to delete
     * @return Result indicating success/failure
     */
    suspend operator fun invoke(taskId: Int): Result<Unit> {
        return tasksRepository.deleteTask(taskId)
    }
}

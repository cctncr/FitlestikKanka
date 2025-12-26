package com.example.fitlestikkanka.tasks.domain.usecase

import com.example.fitlestikkanka.tasks.domain.model.Task
import com.example.fitlestikkanka.tasks.domain.model.TaskStatus
import com.example.fitlestikkanka.tasks.domain.repository.TasksRepository

/**
 * Use case for updating task status.
 *
 * Encapsulates the business logic of changing a task's status.
 */
class UpdateTaskStatusUseCase(
    private val tasksRepository: TasksRepository
) {
    /**
     * Execute update task status operation.
     *
     * @param taskId Task ID to update
     * @param status New status
     * @return Result containing updated Task on success
     */
    suspend operator fun invoke(taskId: Int, status: TaskStatus): Result<Task> {
        return tasksRepository.updateTaskStatus(taskId, status)
    }
}

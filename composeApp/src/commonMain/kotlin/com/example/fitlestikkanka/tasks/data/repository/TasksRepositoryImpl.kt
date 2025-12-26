package com.example.fitlestikkanka.tasks.data.repository

import com.example.fitlestikkanka.tasks.data.datasource.remote.TasksApiService
import com.example.fitlestikkanka.tasks.domain.model.Task
import com.example.fitlestikkanka.tasks.domain.repository.TasksRepository

/**
 * Implementation of TasksRepository.
 *
 * Coordinates data operations between remote API service and domain layer.
 * Maps API strings to domain Task entities.
 */
class TasksRepositoryImpl(
    private val apiService: TasksApiService
) : TasksRepository {

    override suspend fun loadTasks(): Result<List<Task>> {
        return apiService.getTasks().map { stringList ->
            stringList.map { Task(description = it) }
        }
    }
}

package com.example.fitlestikkanka.tasks.data.repository

import com.example.fitlestikkanka.tasks.data.datasource.remote.TasksApiService
import com.example.fitlestikkanka.tasks.data.mapper.TaskMapper
import com.example.fitlestikkanka.tasks.domain.model.Task
import com.example.fitlestikkanka.tasks.domain.model.TaskStatus
import com.example.fitlestikkanka.tasks.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Implementation of TasksRepository.
 *
 * Coordinates remote API data source with auto-refresh capability.
 * Maps DTOs to domain models using TaskMapper.
 */
class TasksRepositoryImpl(
    private val apiService: TasksApiService
) : TasksRepository {

    private val _tasksFlow = MutableStateFlow<List<Task>>(emptyList())

    override suspend fun loadTasks(status: TaskStatus?): Result<List<Task>> {
        return apiService.getTasks(status?.name?.lowercase()).map { dtos ->
            val tasks = dtos.map { TaskMapper.toDomain(it) }
            _tasksFlow.value = tasks
            tasks
        }
    }

    override suspend fun updateTaskStatus(taskId: Int, status: TaskStatus): Result<Task> {
        return apiService.updateTask(taskId, status).map { dto ->
            TaskMapper.toDomain(dto).also {
                // Refresh tasks list after update
                loadTasks()
            }
        }
    }

    override suspend fun deleteTask(taskId: Int): Result<Unit> {
        return apiService.deleteTask(taskId).onSuccess {
            // Refresh tasks list after delete
            loadTasks()
        }
    }

    override fun observeTasks(): Flow<List<Task>> = _tasksFlow.asStateFlow()
}

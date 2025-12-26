package com.example.fitlestikkanka.tasks.data.datasource.remote

import com.example.fitlestikkanka.tasks.data.datasource.remote.dto.TaskDto
import com.example.fitlestikkanka.tasks.data.mapper.TaskMapper
import com.example.fitlestikkanka.tasks.domain.model.TaskStatus
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * Implementation of TasksApiService using Ktor HttpClient.
 *
 * Makes HTTP requests to FastAPI backend at localhost:8000.
 */
class TasksApiServiceImpl(
    private val httpClient: HttpClient
) : TasksApiService {

    companion object {
        private const val BASE_URL = "http://localhost:8000"
    }

    override suspend fun getTasks(status: String?): Result<List<TaskDto>> = try {
        val url = buildString {
            append("$BASE_URL/api/tasks")
            status?.let { append("?status=$it") }
        }
        val response = httpClient.get(url)
        Result.success(response.body<List<TaskDto>>())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateTask(taskId: Int, status: TaskStatus): Result<TaskDto> = try {
        val response = httpClient.put("$BASE_URL/api/tasks/$taskId") {
            contentType(ContentType.Application.Json)
            setBody(TaskMapper.toUpdateDto(status))
        }
        Result.success(response.body<TaskDto>())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteTask(taskId: Int): Result<Unit> = try {
        httpClient.delete("$BASE_URL/api/tasks/$taskId")
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

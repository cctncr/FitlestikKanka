package com.example.fitlestikkanka.tasks.data.datasource.remote

import com.example.fitlestikkanka.auth.domain.repository.AuthRepository
import com.example.fitlestikkanka.core.config.ApiConfig
import com.example.fitlestikkanka.tasks.data.datasource.remote.dto.TaskDto
import com.example.fitlestikkanka.tasks.data.mapper.TaskMapper
import com.example.fitlestikkanka.tasks.domain.model.TaskStatus
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*

/**
 * Implementation of TasksApiService using Ktor HttpClient.
 *
 * Makes HTTP requests to FastAPI backend at localhost:8000.
 */
class TasksApiServiceImpl(
    private val httpClient: HttpClient,
    private val authRepository: AuthRepository
) : TasksApiService {

    override suspend fun getTasks(status: String?): Result<List<TaskDto>> = try {
        val token = authRepository.getStoredToken()?.accessToken
            ?: return Result.failure(Exception("User not authenticated"))

        val url = buildString {
            append("${ApiConfig.BASE_URL}/api/tasks")
            status?.let { append("?status=$it") }
        }

        val response = httpClient.get(url) {
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        if (response.status.isSuccess()) {
            Result.success(response.body<List<TaskDto>>())
        } else {
            val errorBody = response.bodyAsText()
            println("getTasks failed with status ${response.status}: $errorBody")
            Result.failure(Exception("getTasks failed with status ${response.status}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateTask(taskId: Int, status: TaskStatus): Result<TaskDto> = try {
        val token = authRepository.getStoredToken()?.accessToken
            ?: return Result.failure(Exception("User not authenticated"))

        val response = httpClient.put("${ApiConfig.BASE_URL}/api/tasks/$taskId") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(TaskMapper.toUpdateDto(status))
        }

        if (response.status.isSuccess()) {
            Result.success(response.body<TaskDto>())
        } else {
            val errorBody = response.bodyAsText()
            println("updateTask failed with status ${response.status}: $errorBody")
            Result.failure(Exception("updateTask failed with status ${response.status}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteTask(taskId: Int): Result<Unit> = try {
        val token = authRepository.getStoredToken()?.accessToken
            ?: return Result.failure(Exception("User not authenticated"))

        val response = httpClient.delete("${ApiConfig.BASE_URL}/api/tasks/$taskId") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        if (response.status.isSuccess()) {
            Result.success(Unit)
        } else {
            val errorBody = response.bodyAsText()
            println("deleteTask failed with status ${response.status}: $errorBody")
            Result.failure(Exception("deleteTask failed with status ${response.status}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

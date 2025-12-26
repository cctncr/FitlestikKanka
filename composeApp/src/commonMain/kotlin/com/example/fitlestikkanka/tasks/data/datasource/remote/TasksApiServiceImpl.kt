package com.example.fitlestikkanka.tasks.data.datasource.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * Implementation of TasksApiService using Ktor HttpClient.
 *
 * Makes HTTP GET requests to backend API for tasks data.
 */
class TasksApiServiceImpl(
    private val httpClient: HttpClient
) : TasksApiService {

    companion object {
        // TODO: Replace with actual backend URL
        private const val BASE_URL = "https://placeholder-api.example.com"
    }

    override suspend fun getTasks(): Result<List<String>> = try {
        val response = httpClient.get("$BASE_URL/api/tasks")
        Result.success(response.body<List<String>>())
    } catch (e: Exception) {
        Result.failure(e)
    }
}

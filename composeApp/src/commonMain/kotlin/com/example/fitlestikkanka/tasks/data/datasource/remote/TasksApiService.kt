package com.example.fitlestikkanka.tasks.data.datasource.remote

/**
 * API service interface for tasks remote data operations.
 *
 * Defines contract for backend communication.
 */
interface TasksApiService {
    /**
     * Fetches tasks from backend.
     *
     * @return Result containing list of task strings or error
     */
    suspend fun getTasks(): Result<List<String>>
}

package com.example.fitlestikkanka.chat.data.datasource.remote

import com.example.fitlestikkanka.auth.domain.repository.AuthRepository
import com.example.fitlestikkanka.chat.data.datasource.remote.dto.MessageDto
import com.example.fitlestikkanka.core.config.ApiConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*

/**
 * Implementation of MessageApiService using Ktor HttpClient.
 *
 * Fetches message history from backend REST API.
 */
class MessageApiServiceImpl(
    private val httpClient: HttpClient,
    private val authRepository: AuthRepository
) : MessageApiService {

    override suspend fun fetchMessages(
        otherUserId: String,
        limit: Int
    ): Result<List<MessageDto>> = try {
        val token = authRepository.getStoredToken()?.accessToken
            ?: return Result.failure(Exception("User not authenticated"))

        println("DEBUG: fetchMessages - Token: ${token.take(20)}...")
        println("DEBUG: fetchMessages - URL: ${ApiConfig.BASE_URL}/api/messages/")
        println("DEBUG: fetchMessages - Query: other_user_id=$otherUserId, limit=$limit")

        val response = httpClient.get("${ApiConfig.BASE_URL}/api/messages/") {
            header(HttpHeaders.Authorization, "Bearer $token")
            parameter("other_user_id", otherUserId)
            parameter("limit", limit)
        }

        if (response.status.isSuccess()) {
            val messages = response.body<List<MessageDto>>()
            println("DEBUG: fetchMessages - Success, fetched ${messages.size} messages")
            Result.success(messages)
        } else {
            val errorBody = response.bodyAsText()
            println("fetchMessages failed with status ${response.status}: $errorBody")
            Result.failure(Exception("fetchMessages failed with status ${response.status}: $errorBody"))
        }
    } catch (e: Exception) {
        println("fetchMessages exception: ${e.message}")
        e.printStackTrace()
        Result.failure(e)
    }
}

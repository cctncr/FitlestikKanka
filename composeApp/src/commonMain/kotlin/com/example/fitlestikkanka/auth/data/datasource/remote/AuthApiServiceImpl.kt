package com.example.fitlestikkanka.auth.data.datasource.remote

import com.example.fitlestikkanka.auth.data.datasource.remote.dto.LoginResponseDto
import com.example.fitlestikkanka.auth.data.datasource.remote.dto.UserDto
import com.example.fitlestikkanka.core.config.ApiConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*

/**
 * Implementation of AuthApiService using Ktor HttpClient.
 *
 * Makes HTTP requests to FastAPI backend at localhost:8000.
 */
class AuthApiServiceImpl(
    private val httpClient: HttpClient
) : AuthApiService {

    override suspend fun login(username: String, password: String): Result<LoginResponseDto> = try {
        val response = httpClient.post("${ApiConfig.BASE_URL}/api/auth/login") {
            setBody(FormDataContent(Parameters.build {
                append("username", username)
                append("password", password)
                append("grant_type", "password")
            }))
        }

        if (response.status.isSuccess()) {
            Result.success(response.body<LoginResponseDto>())
        } else {
            val errorBody = response.bodyAsText()
            println("Login failed with status ${response.status}: $errorBody")
            Result.failure(Exception("Login failed with status ${response.status}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getCurrentUser(token: String): Result<UserDto> = try {
        val response = httpClient.get("${ApiConfig.BASE_URL}/api/auth/me") {
            header("Authorization", "Bearer $token")
        }
        if (response.status.isSuccess()) {
            Result.success(response.body<UserDto>())
        } else {
            val errorBody = response.bodyAsText()
            println("getCurrentUser failed with status ${response.status}: $errorBody")
            Result.failure(Exception("getCurrentUser failed with status ${response.status}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

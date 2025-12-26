package com.example.fitlestikkanka.auth.data.datasource.remote

import com.example.fitlestikkanka.auth.data.datasource.remote.dto.LoginRequestDto
import com.example.fitlestikkanka.auth.data.datasource.remote.dto.LoginResponseDto
import com.example.fitlestikkanka.auth.data.datasource.remote.dto.UserDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * Implementation of AuthApiService using Ktor HttpClient.
 *
 * Makes HTTP requests to FastAPI backend at localhost:8000.
 */
class AuthApiServiceImpl(
    private val httpClient: HttpClient
) : AuthApiService {

    companion object {
        private const val BASE_URL = "http://localhost:8000"
    }

    override suspend fun login(username: String, password: String): Result<LoginResponseDto> = try {
        val response = httpClient.post("$BASE_URL/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequestDto(username, password))
        }
        Result.success(response.body<LoginResponseDto>())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getCurrentUser(token: String): Result<UserDto> = try {
        val response = httpClient.get("$BASE_URL/api/auth/me") {
            header("Authorization", "Bearer $token")
        }
        Result.success(response.body<UserDto>())
    } catch (e: Exception) {
        Result.failure(e)
    }
}

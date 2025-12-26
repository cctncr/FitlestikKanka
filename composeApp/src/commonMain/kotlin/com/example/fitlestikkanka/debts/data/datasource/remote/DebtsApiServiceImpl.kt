package com.example.fitlestikkanka.debts.data.datasource.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * Implementation of DebtsApiService using Ktor HttpClient.
 *
 * Makes HTTP GET requests to backend API for debts data.
 */
class DebtsApiServiceImpl(
    private val httpClient: HttpClient
) : DebtsApiService {

    companion object {
        // TODO: Replace with actual backend URL
        private const val BASE_URL = "https://placeholder-api.example.com"
    }

    override suspend fun getDebts(): Result<List<String>> = try {
        val response = httpClient.get("$BASE_URL/api/debts")
        Result.success(response.body<List<String>>())
    } catch (e: Exception) {
        Result.failure(e)
    }
}

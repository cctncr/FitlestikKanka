package com.example.fitlestikkanka.debts.data.datasource.remote

import com.example.fitlestikkanka.auth.domain.repository.AuthRepository
import com.example.fitlestikkanka.core.config.ApiConfig
import com.example.fitlestikkanka.debts.data.datasource.remote.dto.DebtBalanceDto
import com.example.fitlestikkanka.debts.data.datasource.remote.dto.DebtDto
import com.example.fitlestikkanka.debts.data.datasource.remote.dto.SettleDebtRequestDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*

/**
 * Implementation of DebtsApiService using Ktor HttpClient.
 *
 * Makes HTTP requests to FastAPI backend at localhost:8000.
 */
class DebtsApiServiceImpl(
    private val httpClient: HttpClient,
    private val authRepository: AuthRepository
) : DebtsApiService {

    override suspend fun getBalance(): Result<List<DebtBalanceDto>> = try {
        val token = authRepository.getStoredToken()?.accessToken
            ?: return Result.failure(Exception("User not authenticated"))

        println("DEBUG: getBalance - Token: ${token.take(20)}...")
        println("DEBUG: getBalance - URL: ${ApiConfig.BASE_URL}/api/debts/balance")

        val response = httpClient.get("${ApiConfig.BASE_URL}/api/debts/balance") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        if (response.status.isSuccess()) {
            // Backend returns single object, wrap in list
            val balanceDto = response.body<DebtBalanceDto>()
            Result.success(listOf(balanceDto))
        } else {
            val errorBody = response.bodyAsText()
            println("getBalance failed with status ${response.status}: $errorBody")
            Result.failure(Exception("getBalance failed with status ${response.status}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getHistory(): Result<List<DebtDto>> = try {
        val token = authRepository.getStoredToken()?.accessToken
            ?: return Result.failure(Exception("User not authenticated"))

        val response = httpClient.get("${ApiConfig.BASE_URL}/api/debts/history") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        if (response.status.isSuccess()) {
            Result.success(response.body<List<DebtDto>>())
        } else {
            val errorBody = response.bodyAsText()
            println("getHistory failed with status ${response.status}: $errorBody")
            Result.failure(Exception("getHistory failed with status ${response.status}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun settleDebt(userId: Int, amount: Double): Result<Unit> = try {
        val token = authRepository.getStoredToken()?.accessToken
            ?: return Result.failure(Exception("User not authenticated"))

        val response = httpClient.post("${ApiConfig.BASE_URL}/api/debts/settle") {
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(SettleDebtRequestDto(userId, amount))
        }

        if (response.status.isSuccess()) {
            Result.success(Unit)
        } else {
            val errorBody = response.bodyAsText()
            println("settleDebt failed with status ${response.status}: $errorBody")
            Result.failure(Exception("settleDebt failed with status ${response.status}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

package com.example.fitlestikkanka.debts.data.datasource.remote

import com.example.fitlestikkanka.debts.data.datasource.remote.dto.DebtBalanceDto
import com.example.fitlestikkanka.debts.data.datasource.remote.dto.DebtDto
import com.example.fitlestikkanka.debts.data.datasource.remote.dto.SettleDebtRequestDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * Implementation of DebtsApiService using Ktor HttpClient.
 *
 * Makes HTTP requests to FastAPI backend at localhost:8000.
 */
class DebtsApiServiceImpl(
    private val httpClient: HttpClient
) : DebtsApiService {

    companion object {
        private const val BASE_URL = "http://localhost:8000"
    }

    override suspend fun getBalance(): Result<List<DebtBalanceDto>> = try {
        val response = httpClient.get("$BASE_URL/api/debts/balance")
        Result.success(response.body<List<DebtBalanceDto>>())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getHistory(): Result<List<DebtDto>> = try {
        val response = httpClient.get("$BASE_URL/api/debts/history")
        Result.success(response.body<List<DebtDto>>())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun settleDebt(userId: Int, amount: Double): Result<Unit> = try {
        httpClient.post("$BASE_URL/api/debts/settle") {
            contentType(ContentType.Application.Json)
            setBody(SettleDebtRequestDto(userId, amount))
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

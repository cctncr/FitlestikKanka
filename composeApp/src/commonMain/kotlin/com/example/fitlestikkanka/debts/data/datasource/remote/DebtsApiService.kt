package com.example.fitlestikkanka.debts.data.datasource.remote

import com.example.fitlestikkanka.debts.data.datasource.remote.dto.DebtBalanceDto
import com.example.fitlestikkanka.debts.data.datasource.remote.dto.DebtDto

/**
 * API service interface for debts endpoints.
 *
 * Defines backend debts operations.
 */
interface DebtsApiService {
    /**
     * Get debt balance summary.
     *
     * @return Result containing list of DebtBalanceDto (aggregated balances per user)
     */
    suspend fun getBalance(): Result<List<DebtBalanceDto>>

    /**
     * Get debt transaction history.
     *
     * @return Result containing list of DebtDto (individual debt records)
     */
    suspend fun getHistory(): Result<List<DebtDto>>

    /**
     * Settle debt with a user.
     *
     * @param userId User ID to settle debt with
     * @param amount Amount to settle
     * @return Result indicating success/failure
     */
    suspend fun settleDebt(userId: Int, amount: Double): Result<Unit>
}

package com.example.fitlestikkanka.debts.domain.repository

import com.example.fitlestikkanka.debts.domain.model.Debt
import com.example.fitlestikkanka.debts.domain.model.DebtBalance
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for debts data operations.
 *
 * Provides abstraction over data sources (remote API).
 */
interface DebtsRepository {
    /**
     * Load debt balance summary.
     *
     * @return Result containing list of debt balances or error
     */
    suspend fun loadBalance(): Result<List<DebtBalance>>

    /**
     * Load debt transaction history.
     *
     * @return Result containing list of debts or error
     */
    suspend fun loadHistory(): Result<List<Debt>>

    /**
     * Settle debt with a user.
     *
     * @param userId User ID to settle with
     * @param amount Amount to settle
     * @return Result indicating success/failure
     */
    suspend fun settleDebt(userId: Int, amount: Double): Result<Unit>

    /**
     * Observe debt balance for auto-refresh.
     *
     * Emits updated balance when debts change (e.g., from AI classification).
     *
     * @return Flow of debt balance lists
     */
    fun observeBalance(): Flow<List<DebtBalance>>
}

package com.example.fitlestikkanka.debts.data.datasource.remote

/**
 * API service interface for debts remote data operations.
 *
 * Defines contract for backend communication.
 */
interface DebtsApiService {
    /**
     * Fetches debts from backend.
     *
     * @return Result containing list of debt strings or error
     */
    suspend fun getDebts(): Result<List<String>>
}

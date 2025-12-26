package com.example.fitlestikkanka.debts.domain.repository

import com.example.fitlestikkanka.debts.domain.model.Debt

/**
 * Repository interface for debts data operations.
 *
 * Follows Clean Architecture principles - domain layer defines contracts,
 * data layer provides implementations.
 */
interface DebtsRepository {
    /**
     * Loads all debts from backend.
     *
     * @return Result containing list of debts or error
     */
    suspend fun loadDebts(): Result<List<Debt>>
}

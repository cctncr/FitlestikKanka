package com.example.fitlestikkanka.debts.domain.usecase

import com.example.fitlestikkanka.debts.domain.repository.DebtsRepository

/**
 * Use case for settling a debt.
 *
 * Encapsulates the business logic of marking debts as settled.
 */
class SettleDebtUseCase(
    private val debtsRepository: DebtsRepository
) {
    /**
     * Execute settle debt operation.
     *
     * @param userId User ID to settle debt with
     * @param amount Amount to settle
     * @return Result indicating success/failure
     */
    suspend operator fun invoke(userId: Int, amount: Double): Result<Unit> {
        return debtsRepository.settleDebt(userId, amount)
    }
}

package com.example.fitlestikkanka.debts.domain.usecase

import com.example.fitlestikkanka.debts.domain.model.DebtBalance
import com.example.fitlestikkanka.debts.domain.repository.DebtsRepository

/**
 * Use case for loading debt balance summary.
 *
 * Encapsulates the business logic of fetching aggregated debt balances.
 */
class LoadDebtBalanceUseCase(
    private val debtsRepository: DebtsRepository
) {
    /**
     * Execute load debt balance operation.
     *
     * @return Result containing list of DebtBalance on success
     */
    suspend operator fun invoke(): Result<List<DebtBalance>> {
        return debtsRepository.loadBalance()
    }
}

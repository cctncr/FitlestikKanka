package com.example.fitlestikkanka.debts.domain.usecase

import com.example.fitlestikkanka.debts.domain.model.Debt
import com.example.fitlestikkanka.debts.domain.repository.DebtsRepository

/**
 * Use case for loading debt transaction history.
 *
 * Encapsulates the business logic of fetching individual debt records.
 */
class LoadDebtHistoryUseCase(
    private val debtsRepository: DebtsRepository
) {
    /**
     * Execute load debt history operation.
     *
     * @return Result containing list of Debt transactions on success
     */
    suspend operator fun invoke(): Result<List<Debt>> {
        return debtsRepository.loadHistory()
    }
}

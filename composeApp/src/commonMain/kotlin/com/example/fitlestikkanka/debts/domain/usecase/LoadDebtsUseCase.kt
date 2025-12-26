package com.example.fitlestikkanka.debts.domain.usecase

import com.example.fitlestikkanka.debts.domain.model.Debt
import com.example.fitlestikkanka.debts.domain.repository.DebtsRepository

/**
 * Use case for loading debts.
 *
 * Encapsulates business logic for fetching debt list.
 * Single responsibility: coordinate debt loading operation.
 */
class LoadDebtsUseCase(
    private val debtsRepository: DebtsRepository
) {
    /**
     * Executes the use case to load debts.
     *
     * @return Result containing list of debts or error
     */
    suspend operator fun invoke(): Result<List<Debt>> {
        return debtsRepository.loadDebts()
    }
}

package com.example.fitlestikkanka.debts.data.repository

import com.example.fitlestikkanka.debts.data.datasource.remote.DebtsApiService
import com.example.fitlestikkanka.debts.domain.model.Debt
import com.example.fitlestikkanka.debts.domain.repository.DebtsRepository

/**
 * Implementation of DebtsRepository.
 *
 * Coordinates data operations between remote API service and domain layer.
 * Maps API strings to domain Debt entities.
 */
class DebtsRepositoryImpl(
    private val apiService: DebtsApiService
) : DebtsRepository {

    override suspend fun loadDebts(): Result<List<Debt>> {
        return apiService.getDebts().map { stringList ->
            stringList.map { Debt(description = it) }
        }
    }
}

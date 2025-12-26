package com.example.fitlestikkanka.debts.data.repository

import com.example.fitlestikkanka.debts.data.datasource.remote.DebtsApiService
import com.example.fitlestikkanka.debts.data.mapper.DebtMapper
import com.example.fitlestikkanka.debts.domain.model.Debt
import com.example.fitlestikkanka.debts.domain.model.DebtBalance
import com.example.fitlestikkanka.debts.domain.repository.DebtsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Implementation of DebtsRepository.
 *
 * Coordinates remote API data source with auto-refresh capability.
 * Maps DTOs to domain models using DebtMapper.
 */
class DebtsRepositoryImpl(
    private val apiService: DebtsApiService
) : DebtsRepository {

    private val _balanceFlow = MutableStateFlow<List<DebtBalance>>(emptyList())

    override suspend fun loadBalance(): Result<List<DebtBalance>> {
        return apiService.getBalance().map { dtos ->
            val balances = dtos.map { DebtMapper.balanceToDomain(it) }
            _balanceFlow.value = balances
            balances
        }
    }

    override suspend fun loadHistory(): Result<List<Debt>> {
        return apiService.getHistory().map { dtos ->
            dtos.map { DebtMapper.toDomain(it) }
        }
    }

    override suspend fun settleDebt(userId: Int, amount: Double): Result<Unit> {
        return apiService.settleDebt(userId, amount).onSuccess {
            // Refresh balance after settling
            loadBalance()
        }
    }

    override fun observeBalance(): Flow<List<DebtBalance>> = _balanceFlow.asStateFlow()
}

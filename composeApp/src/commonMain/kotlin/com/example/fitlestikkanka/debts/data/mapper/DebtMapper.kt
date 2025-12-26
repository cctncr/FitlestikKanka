package com.example.fitlestikkanka.debts.data.mapper

import com.example.fitlestikkanka.debts.data.datasource.remote.dto.DebtBalanceDto
import com.example.fitlestikkanka.debts.data.datasource.remote.dto.DebtDto
import com.example.fitlestikkanka.debts.domain.model.Debt
import com.example.fitlestikkanka.debts.domain.model.DebtBalance
import kotlinx.datetime.Instant

/**
 * Mapper between Debt DTOs and domain models.
 */
object DebtMapper {
    /**
     * Map DebtDto to Debt domain model.
     */
    fun toDomain(dto: DebtDto): Debt {
        return Debt(
            id = dto.id,
            debtorId = dto.debtorId,
            creditorId = dto.creditorId,
            amount = dto.amount,
            isSettled = dto.isSettled,
            createdAt = Instant.parse(dto.createdAt)
        )
    }

    /**
     * Map DebtBalanceDto to DebtBalance domain model.
     */
    fun balanceToDomain(dto: DebtBalanceDto): DebtBalance {
        return DebtBalance(
            userId = dto.userId,
            username = dto.username,
            totalOwed = dto.totalOwed,
            totalOwing = dto.totalOwing
        )
    }
}

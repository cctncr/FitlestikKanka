package com.example.fitlestikkanka.debts.domain.model

import kotlinx.datetime.Instant

/**
 * Domain entity representing a debt.
 *
 * Matches backend Debt model with full schema.
 */
data class Debt(
    val id: Int,
    val debtorId: Int,
    val creditorId: Int,
    val amount: Double,
    val isSettled: Boolean,
    val createdAt: Instant
)

/**
 * Aggregated debt balance between users.
 *
 * Used to display summary of debts owed/owing.
 */
data class DebtBalance(
    val userId: Int? = null,      // Optional - backend may not send
    val username: String,
    val totalOwed: Double,        // Amount others owe to you
    val totalToCollect: Double,   // Amount you can collect from others
    val netBalance: Double        // Net balance (positive = owed to you, negative = you owe)
)

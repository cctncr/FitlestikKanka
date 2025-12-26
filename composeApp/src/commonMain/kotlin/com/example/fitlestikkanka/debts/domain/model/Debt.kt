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
    val userId: Int,
    val username: String,
    val totalOwed: Double,     // Amount others owe to you
    val totalOwing: Double     // Amount you owe to others
)

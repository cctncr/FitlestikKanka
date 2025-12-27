package com.example.fitlestikkanka.debts.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for Debt data from backend.
 *
 * Received from GET /api/debts/history endpoint.
 */
@Serializable
data class DebtDto(
    val id: Int,
    @SerialName("debtor_id")
    val debtorId: Int,
    @SerialName("creditor_id")
    val creditorId: Int,
    val amount: Double,
    @SerialName("is_settled")
    val isSettled: Boolean,
    @SerialName("created_at")
    val createdAt: String  // ISO timestamp
)

/**
 * DTO for debt balance summary.
 *
 * Received from GET /api/debts/balance endpoint.
 */
@Serializable
data class DebtBalanceDto(
    @SerialName("user_id")  // Try correct field name first
    val userId: Int? = null,  // Make optional in case backend doesn't send it
    val username: String,
    @SerialName("total_owed")
    val totalOwed: Double,
    @SerialName("total_to_collect")  // Backend uses this instead of total_owing
    val totalToCollect: Double,
    @SerialName("net_balance")
    val netBalance: Double
)

/**
 * DTO for settling a debt.
 *
 * Sent to POST /api/debts/settle endpoint.
 */
@Serializable
data class SettleDebtRequestDto(
    @SerialName("user_id")
    val userId: Int,
    val amount: Double
)

package com.example.fitlestikkanka.tasks.domain.model

import kotlinx.datetime.Instant

/**
 * Domain entity representing a task.
 *
 * Matches backend Task model with full schema.
 */
data class Task(
    val id: Int,
    val itemName: String,
    val status: TaskStatus,
    val creatorId: Int? = null,  // Optional - backend may not return
    val assigneeId: Int? = null,  // Optional - backend may not return
    val createdAt: Instant,
    val completedAt: Instant? = null
)

/**
 * Task status enum matching backend values.
 */
enum class TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED;

    companion object {
        /**
         * Parse status from backend string (case-insensitive).
         */
        fun fromString(value: String): TaskStatus =
            valueOf(value.uppercase())
    }
}

package com.example.fitlestikkanka.tasks.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for Task data from backend.
 *
 * Received from GET /api/tasks endpoint.
 */
@Serializable
data class TaskDto(
    val id: Int,
    @SerialName("item_name")
    val itemName: String,
    val status: String,
    @SerialName("creator_id")
    val creatorId: Int,
    @SerialName("assignee_id")
    val assigneeId: Int,
    @SerialName("created_at")
    val createdAt: String,  // ISO timestamp
    @SerialName("completed_at")
    val completedAt: String? = null
)

/**
 * DTO for updating task status.
 *
 * Sent to PUT /api/tasks/{id} endpoint.
 */
@Serializable
data class TaskUpdateDto(
    val status: String
)

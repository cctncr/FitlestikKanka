package com.example.fitlestikkanka.tasks.data.mapper

import com.example.fitlestikkanka.tasks.data.datasource.remote.dto.TaskDto
import com.example.fitlestikkanka.tasks.data.datasource.remote.dto.TaskUpdateDto
import com.example.fitlestikkanka.tasks.domain.model.Task
import com.example.fitlestikkanka.tasks.domain.model.TaskStatus
import kotlinx.datetime.Instant

/**
 * Mapper between Task DTOs and domain models.
 */
object TaskMapper {
    /**
     * Map TaskDto to Task domain model.
     */
    fun toDomain(dto: TaskDto): Task {
        return Task(
            id = dto.id,
            itemName = dto.itemName,
            status = TaskStatus.fromString(dto.status),
            creatorId = dto.creatorId,
            assigneeId = dto.assigneeId,
            createdAt = Instant.parse(dto.createdAt),
            completedAt = dto.completedAt?.let { Instant.parse(it) }
        )
    }

    /**
     * Map Task domain model to TaskDto.
     */
    fun toDto(task: Task): TaskDto {
        return TaskDto(
            id = task.id,
            itemName = task.itemName,
            status = task.status.name.lowercase(),
            creatorId = task.creatorId,
            assigneeId = task.assigneeId,
            createdAt = task.createdAt.toString(),
            completedAt = task.completedAt?.toString()
        )
    }

    /**
     * Map TaskStatus to TaskUpdateDto.
     */
    fun toUpdateDto(status: TaskStatus): TaskUpdateDto {
        return TaskUpdateDto(status = status.name.lowercase())
    }
}

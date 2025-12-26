package com.example.fitlestikkanka.chat.data.mapper

import com.example.fitlestikkanka.chat.data.datasource.remote.dto.MessageDto
import com.example.fitlestikkanka.chat.domain.model.Message
import com.example.fitlestikkanka.chat.domain.model.MessageStatus
import kotlinx.datetime.Instant

/**
 * Mapper for converting between Message domain entity and MessageDto.
 * Follows the Mapper pattern to keep domain and data layers separated.
 */
object MessageMapper {
    /**
     * Converts MessageDto (network layer) to Message (domain layer).
     *
     * @param dto The DTO received from network or database
     * @param currentUserId ID of current user to determine message direction
     * @return Domain Message entity
     */
    fun toDomain(dto: MessageDto, currentUserId: String): Message {
        return Message(
            id = dto.id,
            conversationId = dto.conversationId,
            content = dto.content,
            senderId = dto.senderId,
            timestamp = Instant.fromEpochMilliseconds(dto.timestamp),
            status = MessageStatus.valueOf(dto.status),
            isFromCurrentUser = dto.senderId == currentUserId
        )
    }

    /**
     * Converts Message (domain layer) to MessageDto (network layer).
     *
     * @param message The domain entity to convert
     * @return Network DTO ready for serialization
     */
    fun toDto(message: Message): MessageDto {
        return MessageDto(
            id = message.id,
            conversationId = message.conversationId,
            content = message.content,
            senderId = message.senderId,
            timestamp = message.timestamp.toEpochMilliseconds(),
            status = message.status.name
        )
    }
}

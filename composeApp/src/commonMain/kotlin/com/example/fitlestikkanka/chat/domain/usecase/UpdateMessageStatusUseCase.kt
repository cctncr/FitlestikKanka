package com.example.fitlestikkanka.chat.domain.usecase

import com.example.fitlestikkanka.chat.domain.model.MessageStatus
import com.example.fitlestikkanka.chat.domain.repository.MessageRepository

/**
 * Use case for updating the status of a message.
 * Used when messages are delivered or read by the recipient.
 *
 * Status progression: SENDING → SENT → DELIVERED → READ
 */
class UpdateMessageStatusUseCase(
    private val messageRepository: MessageRepository
) {
    /**
     * Executes the use case to update message status.
     *
     * @param messageId ID of the message to update
     * @param status New status to set
     * @return Result indicating success or failure
     */
    suspend operator fun invoke(messageId: String, status: MessageStatus): Result<Unit> {
        return messageRepository.updateMessageStatus(messageId, status)
    }
}

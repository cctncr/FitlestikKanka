package com.example.fitlestikkanka.chat.domain.usecase

import com.example.fitlestikkanka.chat.domain.model.Message
import com.example.fitlestikkanka.chat.domain.repository.MessageRepository

/**
 * Use case for sending a new message in a conversation.
 * Encapsulates the business logic for message creation and validation.
 *
 * This use case:
 * 1. Validates message content
 * 2. Delegates to repository for persistence and network sync
 * 3. Returns the created message or error
 */
class SendMessageUseCase(
    private val messageRepository: MessageRepository
) {
    /**
     * Executes the use case to send a message.
     *
     * @param conversationId ID of the conversation
     * @param content Text content of the message (will be trimmed)
     * @param senderId ID of the user sending the message
     * @return Result containing the created Message on success, or error
     */
    suspend operator fun invoke(
        conversationId: String,
        content: String,
        senderId: String
    ): Result<Message> {
        // Validate content
        val trimmedContent = content.trim()
        if (trimmedContent.isBlank()) {
            return Result.failure(IllegalArgumentException("Message content cannot be empty"))
        }

        // Delegate to repository
        return messageRepository.sendMessage(
            conversationId = conversationId,
            content = trimmedContent,
            senderId = senderId
        )
    }
}

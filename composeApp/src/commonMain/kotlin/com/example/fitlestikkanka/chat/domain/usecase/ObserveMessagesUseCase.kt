package com.example.fitlestikkanka.chat.domain.usecase

import com.example.fitlestikkanka.chat.domain.model.Message
import com.example.fitlestikkanka.chat.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for observing messages in a conversation as a reactive stream.
 * Provides a Flow that emits whenever messages change (added, updated, or deleted).
 *
 * This use case enables real-time message updates in the UI.
 */
class ObserveMessagesUseCase(
    private val messageRepository: MessageRepository
) {
    /**
     * Executes the use case to observe messages.
     *
     * @param conversationId ID of the conversation to observe
     * @return Flow of message lists, sorted by timestamp descending (newest first)
     */
    operator fun invoke(conversationId: String): Flow<List<Message>> {
        return messageRepository.observeMessages(conversationId)
    }
}

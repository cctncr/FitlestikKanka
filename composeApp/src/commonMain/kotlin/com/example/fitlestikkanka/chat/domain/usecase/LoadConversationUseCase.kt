package com.example.fitlestikkanka.chat.domain.usecase

import com.example.fitlestikkanka.chat.domain.model.Conversation
import com.example.fitlestikkanka.chat.domain.repository.ConversationRepository

/**
 * Use case for loading a conversation and its metadata.
 * Fetches conversation details including participants and last message.
 */
class LoadConversationUseCase(
    private val conversationRepository: ConversationRepository
) {
    /**
     * Executes the use case to load a conversation.
     *
     * @param conversationId ID of the conversation to load
     * @return Result containing the Conversation on success, or error
     */
    suspend operator fun invoke(conversationId: String): Result<Conversation> {
        return conversationRepository.getConversation(conversationId)
    }
}

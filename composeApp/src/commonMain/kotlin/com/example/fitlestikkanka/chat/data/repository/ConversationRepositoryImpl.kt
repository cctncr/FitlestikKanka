package com.example.fitlestikkanka.chat.data.repository

import com.example.fitlestikkanka.chat.domain.model.Conversation
import com.example.fitlestikkanka.chat.domain.model.Participant
import com.example.fitlestikkanka.chat.domain.repository.ConversationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Stub implementation of ConversationRepository.
 * Returns mock conversation data for testing the chat UI.
 *
 * TODO: Full implementation would:
 * - Fetch conversation metadata from server
 * - Cache in local database
 * - Sync typing indicators via WebSocket
 */
class ConversationRepositoryImpl : ConversationRepository {

    override suspend fun getConversation(conversationId: String): Result<Conversation> {
        // Return mock conversation for testing
        val mockConversation = Conversation(
            id = conversationId,
            participants = listOf(
                Participant(
                    userId = "current-user",
                    name = "You",
                    isCurrentUser = true
                ),
                Participant(
                    userId = "other-user",
                    name = "Yusuf",
                    isOnline = true,
                    isCurrentUser = false
                )
            ),
            lastMessage = null,
            lastMessageTimestamp = null,
            unreadCount = 0
        )

        return Result.success(mockConversation)
    }

    override fun observeConversation(conversationId: String): Flow<Conversation> {
        // Return mock flow for testing
        val mockConversation = Conversation(
            id = conversationId,
            participants = listOf(
                Participant(
                    userId = "current-user",
                    name = "You",
                    isCurrentUser = true
                ),
                Participant(
                    userId = "other-user",
                    name = "Yusuf",
                    isOnline = true,
                    isCurrentUser = false
                )
            ),
            lastMessage = null,
            lastMessageTimestamp = null,
            unreadCount = 0
        )

        return flowOf(mockConversation)
    }

    override suspend fun updateTypingStatus(
        conversationId: String,
        isTyping: Boolean
    ): Result<Unit> {
        // TODO: Send typing status via WebSocket
        return Result.success(Unit)
    }
}

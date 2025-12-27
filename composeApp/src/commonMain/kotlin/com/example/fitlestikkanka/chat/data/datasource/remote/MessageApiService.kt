package com.example.fitlestikkanka.chat.data.datasource.remote

import com.example.fitlestikkanka.chat.data.datasource.remote.dto.MessageDto

/**
 * API service interface for fetching chat messages via REST.
 *
 * Separate from WebSocket for:
 * - Initial message history loading
 * - Pagination support
 * - Offline message sync
 */
interface MessageApiService {
    /**
     * Fetch message history with another user.
     *
     * @param otherUserId ID of the other user in the conversation
     * @param limit Maximum number of messages to fetch
     * @return Result containing list of messages or error
     */
    suspend fun fetchMessages(
        otherUserId: String,
        limit: Int = 50
    ): Result<List<MessageDto>>
}

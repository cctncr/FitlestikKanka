package com.example.fitlestikkanka.chat.data.datasource.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.fitlestikkanka.chat.domain.model.Message
import com.example.fitlestikkanka.chat.domain.model.MessageStatus
import com.example.fitlestikkanka.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant

/**
 * Implementation of MessageLocalDataSource using SQLDelight.
 * Provides local persistence for messages with reactive queries.
 *
 * @property database SQLDelight database instance
 */
class MessageLocalDataSourceImpl(
    private val database: AppDatabase
) : MessageLocalDataSource {

    private val queries = database.messageQueries

    override fun observeMessages(conversationId: String): Flow<List<Message>> {
        return queries.selectByConversation(conversationId)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { dbMessages ->
                dbMessages.map { dbMessage ->
                    Message(
                        id = dbMessage.id,
                        conversationId = dbMessage.conversationId,
                        content = dbMessage.content,
                        senderId = dbMessage.senderId,
                        timestamp = Instant.fromEpochMilliseconds(dbMessage.timestamp),
                        status = MessageStatus.valueOf(dbMessage.status),
                        isFromCurrentUser = false // Will be set by repository
                    )
                }
            }
    }

    override suspend fun insertMessage(message: Message) {
        withContext(Dispatchers.Default) {
            queries.insert(
                id = message.id,
                conversationId = message.conversationId,
                content = message.content,
                senderId = message.senderId,
                timestamp = message.timestamp.toEpochMilliseconds(),
                status = message.status.name
            )
        }
    }

    override suspend fun insertMessages(messages: List<Message>) {
        withContext(Dispatchers.Default) {
            queries.transaction {
                messages.forEach { message ->
                    queries.insert(
                        id = message.id,
                        conversationId = message.conversationId,
                        content = message.content,
                        senderId = message.senderId,
                        timestamp = message.timestamp.toEpochMilliseconds(),
                        status = message.status.name
                    )
                }
            }
        }
    }

    override suspend fun updateMessageStatus(messageId: String, status: MessageStatus) {
        withContext(Dispatchers.Default) {
            queries.updateStatus(
                status = status.name,
                id = messageId
            )
        }
    }

    override suspend fun deleteMessage(messageId: String) {
        withContext(Dispatchers.Default) {
            queries.deleteById(messageId)
        }
    }

    override suspend fun deleteAllMessages(conversationId: String) {
        withContext(Dispatchers.Default) {
            queries.deleteByConversation(conversationId)
        }
    }

    override suspend fun getMessage(messageId: String): Message? {
        return withContext(Dispatchers.Default) {
            val dbMessage = queries.selectById(messageId).executeAsOneOrNull()
            dbMessage?.let {
                Message(
                    id = it.id,
                    conversationId = it.conversationId,
                    content = it.content,
                    senderId = it.senderId,
                    timestamp = Instant.fromEpochMilliseconds(it.timestamp),
                    status = MessageStatus.valueOf(it.status),
                    isFromCurrentUser = false
                )
            }
        }
    }
}

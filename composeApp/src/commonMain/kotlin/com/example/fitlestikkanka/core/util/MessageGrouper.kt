package com.example.fitlestikkanka.core.util

import com.example.fitlestikkanka.chat.domain.model.Message
import com.example.fitlestikkanka.chat.presentation.viewmodel.MessageGroup
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

/**
 * Utility class for grouping messages by date.
 * Organizes messages into groups with human-readable date labels.
 */
class MessageGrouper {
    /**
     * Groups messages by date with labels like "Today", "Yesterday", or formatted dates.
     *
     * @param messages List of messages to group (will be sorted by timestamp descending)
     * @return List of MessageGroup objects, each containing messages for a specific date
     */
    fun groupByDate(messages: List<Message>): List<MessageGroup> {
        val now = TimeUtils.now()
        val today = now.toLocalDateTime(TimeUtils.currentTimeZone()).date

        return messages
            .sortedByDescending { it.timestamp }
            .groupBy { message ->
                val messageDate = message.timestamp
                    .toLocalDateTime(TimeUtils.currentTimeZone())
                    .date

                when {
                    messageDate == today -> "Today"
                    messageDate == today.minus(1, DateTimeUnit.DAY) -> "Yesterday"
                    else -> DateTimeFormatter.formatDate(messageDate)
                }
            }
            .map { (date, msgs) ->
                MessageGroup(date = date, messages = msgs)
            }
    }
}

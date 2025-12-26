package com.example.fitlestikkanka.chat.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitlestikkanka.chat.presentation.viewmodel.MessageGroup

/**
 * Displays a scrollable list of messages grouped by date.
 * STATELESS - accepts message groups and callbacks.
 *
 * Features:
 * - LazyColumn with reverseLayout (newest messages at bottom)
 * - Date separators between message groups
 * - Auto-scroll to newest message
 * - Empty state placeholder
 *
 * @param messageGroups List of message groups organized by date
 * @param currentUserId ID of current user to determine message direction
 * @param onMessageRead Callback when a receiver message becomes visible
 * @param modifier Optional modifier
 */
@Composable
fun MessageList(
    messageGroups: List<MessageGroup>,
    currentUserId: String,
    onMessageRead: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // Auto-scroll to newest message when new messages arrive
    LaunchedEffect(messageGroups.size) {
        if (messageGroups.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    if (messageGroups.isEmpty()) {
        EmptyChatPlaceholder(modifier = modifier)
    } else {
        LazyColumn(
            state = listState,
            reverseLayout = true, // Newest messages at bottom
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = modifier.fillMaxSize()
        ) {
            messageGroups.forEach { group ->
                // Messages in the group
                items(
                    items = group.messages,
                    key = { it.id }
                ) { message ->
                    MessageBubble(
                        message = message,
                        isFromCurrentUser = message.isFromCurrentUser,
                        onMessageRead = onMessageRead,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Date separator
                item(key = "date_${group.date}") {
                    DateSeparator(date = group.date)
                }
            }
        }
    }
}

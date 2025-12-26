package com.example.fitlestikkanka.chat.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitlestikkanka.chat.domain.model.Message

/**
 * Routing composable that decides which message bubble to display.
 * STATELESS - delegates to SenderMessageBubble or ReceiverMessageBubble.
 *
 * Routes based on isFromCurrentUser property.
 *
 * @param message The message to display
 * @param isFromCurrentUser Whether this message was sent by current user
 * @param onMessageRead Callback when receiver message becomes visible
 * @param modifier Optional modifier
 */
@Composable
fun MessageBubble(
    message: Message,
    isFromCurrentUser: Boolean,
    onMessageRead: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isFromCurrentUser) {
        SenderMessageBubble(
            message = message,
            modifier = modifier
        )
    } else {
        ReceiverMessageBubble(
            message = message,
            onMessageRead = onMessageRead,
            modifier = modifier
        )
    }
}

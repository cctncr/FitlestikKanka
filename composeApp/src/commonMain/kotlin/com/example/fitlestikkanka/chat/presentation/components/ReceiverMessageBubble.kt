package com.example.fitlestikkanka.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlestikkanka.chat.domain.model.Message
import com.example.fitlestikkanka.core.util.DateTimeFormatter
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Displays a message received from the other user (receiver).
 * STATELESS (mostly) - accepts message and callback, emits read event via callback.
 *
 * Styled as a dark gray bubble aligned to the left (WhatsApp dark mode style).
 * Shows message content and timestamp.
 * Automatically calls onMessageRead when displayed (LaunchedEffect).
 *
 * @param message The message to display
 * @param onMessageRead Callback when message becomes visible (for read receipts)
 * @param modifier Optional modifier
 */
@Composable
fun ReceiverMessageBubble(
    message: Message,
    onMessageRead: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Mark message as read when displayed
    LaunchedEffect(message.id) {
        onMessageRead(message.id)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 260.dp)
                .background(
                    color = Color(0xFF262D31), // Dark gray for dark mode
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 16.dp
                    )
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = message.content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            val time = message.timestamp
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .time

            Text(
                text = DateTimeFormatter.formatTime(time.hour, time.minute),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.align(androidx.compose.ui.Alignment.End)
            )
        }
    }
}

package com.example.fitlestikkanka.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitlestikkanka.chat.domain.model.Message
import com.example.fitlestikkanka.core.util.DateTimeFormatter
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Displays a message sent by the current user (sender).
 * STATELESS - accepts message as parameter, emits no events.
 *
 * Styled as a green bubble aligned to the right (WhatsApp-style).
 * Shows message content, timestamp, and status indicator.
 *
 * @param message The message to display
 * @param modifier Optional modifier
 */
@Composable
fun SenderMessageBubble(
    message: Message,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 260.dp)
                .background(
                    color = Color(0xFF128C7E), // WhatsApp green
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 4.dp
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                val time = message.timestamp
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .time

                Text(
                    text = DateTimeFormatter.formatTime(time.hour, time.minute),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = Color.White.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.width(4.dp))

                MessageStatusIndicator(
                    status = message.status,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

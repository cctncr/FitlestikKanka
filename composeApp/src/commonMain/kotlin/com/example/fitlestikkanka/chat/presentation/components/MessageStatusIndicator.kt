package com.example.fitlestikkanka.chat.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fitlestikkanka.chat.domain.model.MessageStatus

/**
 * Displays message status as checkmarks (WhatsApp-style).
 * STATELESS - accepts status as parameter, no internal state.
 *
 * Status indicators:
 * - SENDING: Faded single checkmark
 * - SENT: Single checkmark
 * - DELIVERED: Double checkmarks (gray)
 * - READ: Double checkmarks (blue)
 *
 * @param status Current status of the message
 * @param tint Base color for the checkmarks
 * @param modifier Optional modifier
 */
@Composable
fun MessageStatusIndicator(
    status: MessageStatus,
    tint: Color = Color.Gray,
    modifier: Modifier = Modifier
) {
    when (status) {
        MessageStatus.SENDING -> {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Sending",
                tint = tint.copy(alpha = 0.5f),
                modifier = modifier
            )
        }
        MessageStatus.SENT -> {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Sent",
                tint = tint,
                modifier = modifier
            )
        }
        MessageStatus.DELIVERED -> {
            Icon(
                imageVector = Icons.Default.DoneAll,
                contentDescription = "Delivered",
                tint = tint,
                modifier = modifier
            )
        }
        MessageStatus.READ -> {
            Icon(
                imageVector = Icons.Default.DoneAll,
                contentDescription = "Read",
                tint = Color(0xFF53BDEB), // WhatsApp blue
                modifier = modifier
            )
        }
    }
}

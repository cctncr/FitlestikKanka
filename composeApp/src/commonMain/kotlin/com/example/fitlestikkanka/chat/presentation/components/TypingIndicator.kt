package com.example.fitlestikkanka.chat.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Displays an animated typing indicator (3 bouncing dots).
 * STATELESS - animation is handled internally by Compose animation API.
 *
 * Shows when the other user is typing a message.
 * Uses staggered animation for each dot.
 *
 * @param modifier Optional modifier
 */
@Composable
fun TypingIndicator(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val infiniteTransition = rememberInfiniteTransition()
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(index * 200)
                ), label = "typing-indicator-dot-$index"
            )

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = Color.Gray.copy(alpha = alpha),
                        shape = CircleShape
                    )
            )
        }
    }
}

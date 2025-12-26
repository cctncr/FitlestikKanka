package com.example.fitlestikkanka.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Input field for composing and sending messages.
 * STATELESS (mostly) - manages local text input state, emits events via callbacks.
 *
 * Follows state hoisting pattern:
 * - Local state: messageText (UI-only state)
 * - Elevated state: typing status, send message (business logic)
 *
 * @param onSendMessage Callback when user sends a message
 * @param onTypingStatusChanged Callback when typing status changes
 * @param modifier Optional modifier
 */
@Composable
fun MessageInput(
    onSendMessage: (String) -> Unit,
    onTypingStatusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var messageText by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(false) }

    // Notify parent when typing status changes
    LaunchedEffect(messageText) {
        val shouldBeTyping = messageText.isNotBlank()
        if (shouldBeTyping != isTyping) {
            isTyping = shouldBeTyping
            onTypingStatusChanged(shouldBeTyping)
        }
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color(0xFF1E1E1E),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier
                    .weight(1f),
                placeholder = {
                    Text(
                        text = "Type a message...",
                        color = Color.Gray
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF262D31),
                    unfocusedContainerColor = Color(0xFF262D31),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFF128C7E),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(24.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (messageText.isNotBlank()) {
                        onSendMessage(messageText)
                        messageText = ""
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFF128C7E),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send message",
                    tint = Color.White
                )
            }
        }
    }
}

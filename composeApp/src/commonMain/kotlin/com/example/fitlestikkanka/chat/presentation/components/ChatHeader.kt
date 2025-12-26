package com.example.fitlestikkanka.chat.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fitlestikkanka.chat.domain.model.Participant

/**
 * Top bar for the chat screen showing participant info and status.
 * STATELESS - accepts participant data and callbacks.
 *
 * Displays:
 * - Back button
 * - Participant name
 * - Online/offline/typing status
 *
 * @param participant The other user in the conversation
 * @param isTyping Whether the other user is currently typing
 * @param onNavigateBack Callback when back button is clicked
 * @param modifier Optional modifier
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHeader(
    participant: Participant?,
    isTyping: Boolean,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = participant?.name ?: "Unknown",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White
                )
                Text(
                    text = when {
                        isTyping -> "typing..."
                        participant?.isOnline == true -> "online"
                        else -> "offline"
                    },
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = if (isTyping) Color(0xFF128C7E) else Color.Gray
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1E1E1E)
        ),
        modifier = modifier
    )
}

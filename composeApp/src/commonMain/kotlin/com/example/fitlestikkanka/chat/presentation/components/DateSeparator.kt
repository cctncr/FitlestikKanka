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

/**
 * Displays a date separator label in the chat.
 * STATELESS - renders date string without managing state.
 *
 * Shows dates like "Today", "Yesterday", or "Jan 15, 2025"
 * Centered in the chat with a rounded background.
 *
 * @param date Human-readable date string to display
 * @param modifier Optional modifier
 */
@Composable
fun DateSeparator(
    date: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
            color = Color.White.copy(alpha = 0.6f),
            modifier = Modifier
                .background(
                    color = Color(0xFF262D31),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

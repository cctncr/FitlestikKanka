package com.example.fitlestikkanka.core.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.fitlestikkanka.core.navigation.Screen

/**
 * Bottom navigation bar component for app navigation.
 *
 * Displays three navigation items: Chat, Tasks (Görevler), and Debts (Borçlar).
 * Uses WhatsApp-style dark theme with green accent color.
 *
 * @param currentScreen Currently selected screen
 * @param onScreenSelected Callback when a screen is selected
 */
@Composable
fun BottomNavigationBar(
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF1E1E1E)
    ) {
        // Chat tab
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = "Chat"
                )
            },
            label = { Text("Chat") },
            selected = currentScreen == Screen.Chat,
            onClick = { onScreenSelected(Screen.Chat) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF128C7E),  // WhatsApp green
                selectedTextColor = Color(0xFF128C7E),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFF1E1E1E)
            )
        )

        // Tasks tab
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = "Tasks"
                )
            },
            label = { Text("Görevler") },
            selected = currentScreen == Screen.Tasks,
            onClick = { onScreenSelected(Screen.Tasks) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF128C7E),
                selectedTextColor = Color(0xFF128C7E),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFF1E1E1E)
            )
        )

        // Debts tab
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = "Debts"
                )
            },
            label = { Text("Borçlar") },
            selected = currentScreen == Screen.Debts,
            onClick = { onScreenSelected(Screen.Debts) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF128C7E),
                selectedTextColor = Color(0xFF128C7E),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFF1E1E1E)
            )
        )
    }
}

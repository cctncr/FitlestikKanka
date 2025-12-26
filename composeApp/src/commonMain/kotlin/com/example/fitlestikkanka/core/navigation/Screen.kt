package com.example.fitlestikkanka.core.navigation

/**
 * Sealed interface representing app screens for manual navigation management.
 *
 * This provides type-safe screen destinations without external navigation libraries.
 * Used with simple mutableStateOf for screen switching.
 */
sealed interface Screen {
    /**
     * Chat screen - WhatsApp-style messaging interface
     */
    data object Chat : Screen

    /**
     * Tasks screen - Lists user's tasks from backend
     */
    data object Tasks : Screen

    /**
     * Debts screen - Lists user's debts from backend
     */
    data object Debts : Screen
}

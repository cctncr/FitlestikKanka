package com.example.fitlestikkanka.core.util

import kotlinx.datetime.LocalDate

/**
 * Utility object for formatting dates in human-readable format.
 * Used throughout the chat feature for displaying message timestamps.
 */
object DateTimeFormatter {
    /**
     * Formats a LocalDate into a human-readable string.
     * Example: "Jan 15, 2025"
     *
     * @param date The date to format
     * @return Formatted date string
     */
    fun formatDate(date: LocalDate): String {
        val months = listOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

        return "${months[date.monthNumber - 1]} ${date.dayOfMonth}, ${date.year}"
    }

    /**
     * Formats time in HH:MM format with leading zeros.
     * Example: "14:05" or "09:30"
     *
     * @param hour Hour (0-23)
     * @param minute Minute (0-59)
     * @return Formatted time string
     */
    fun formatTime(hour: Int, minute: Int): String {
        return "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
    }
}

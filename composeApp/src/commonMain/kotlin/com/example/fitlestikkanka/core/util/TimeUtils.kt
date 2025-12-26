package com.example.fitlestikkanka.core.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone

/**
 * Utility functions for working with time in a KMP-compatible way.
 */
expect object TimeUtils {
    /**
     * Gets the current timestamp as an Instant.
     */
    fun now(): Instant

    /**
     * Gets the current system timezone.
     */
    fun currentTimeZone(): TimeZone
}

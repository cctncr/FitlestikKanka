package com.example.fitlestikkanka.core.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone

/**
 * JVM implementation of TimeUtils.
 */
actual object TimeUtils {
    actual fun now(): Instant = Clock.System.now()
    actual fun currentTimeZone(): TimeZone = TimeZone.currentSystemDefault()
}

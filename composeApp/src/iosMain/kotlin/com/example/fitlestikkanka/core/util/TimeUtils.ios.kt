package com.example.fitlestikkanka.core.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

/**
 * iOS implementation of TimeUtils.
 */
actual object TimeUtils {
    actual fun now(): Instant {
        val currentTimeMillis = (NSDate().timeIntervalSince1970 * 1000).toLong()
        return Instant.fromEpochMilliseconds(currentTimeMillis)
    }

    actual fun currentTimeZone(): TimeZone = TimeZone.currentSystemDefault()
}

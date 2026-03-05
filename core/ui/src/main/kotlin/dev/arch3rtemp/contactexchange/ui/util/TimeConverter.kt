package dev.arch3rtemp.contactexchange.ui.util

import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Instant
import java.time.LocalDateTime as JavaLocalDateTime

class TimeConverter {

    /**
     * Converts epoch milliseconds to a formatted date string.
     *
     * @param epochMillis The epoch time in milliseconds.
     * @param pattern The date-time pattern, e.g., "yyyy-MM-dd HH:mm:ss".
     * @param locale The locale for formatting.
     * @param timeZone The time zone identifier as a string, e.g., "America/New_York".
     * @return The formatted date string.
     */
    fun convertLongToDateString(epochMillis: Long, pattern: String, locale: Locale, timeZone: String): String {
        val instant = Instant.fromEpochMilliseconds(epochMillis)
        val zone = TimeZone.of(timeZone)
        val localDateTime = instant.toLocalDateTime(zone)

        // Convert kotlinx.datetime.LocalDateTime to kotlin.time.LocalDateTime
        val javaLocalDateTime = JavaLocalDateTime.of(
            localDateTime.year,
            localDateTime.month.number,
            localDateTime.day,
            localDateTime.hour,
            localDateTime.minute,
            localDateTime.second
        )

        // Create a DateTimeFormatter with the specified pattern and locale
        val formatter = DateTimeFormatter.ofPattern(pattern, locale)

        // Format the kotlin.time.LocalDateTime to a string
        return formatter.format(javaLocalDateTime)
    }
}

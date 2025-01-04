package dev.arch3rtemp.ui.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
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

        // Convert kotlinx.datetime.LocalDateTime to java.time.LocalDateTime
        val javaLocalDateTime = JavaLocalDateTime.of(
            localDateTime.year,
            localDateTime.monthNumber,
            localDateTime.dayOfMonth,
            localDateTime.hour,
            localDateTime.minute,
            localDateTime.second
        )

        // Create a DateTimeFormatter with the specified pattern and locale
        val formatter = DateTimeFormatter.ofPattern(pattern, locale)

        // Format the java.time.LocalDateTime to a string
        return formatter.format(javaLocalDateTime)
    }

    /**
     * Converts a formatted date string to epoch milliseconds.
     *
     * @param dateString The date string to parse.
     * @param pattern The date-time pattern, e.g., "yyyy-MM-dd HH:mm:ss".
     * @param locale The locale for parsing.
     * @param timeZone The time zone identifier as a string, e.g., "America/New_York".
     * @return The epoch time in milliseconds, or null if parsing fails.
     */
    fun convertDateStringToLong(dateString: String, pattern: String, locale: Locale, timeZone: String): Long? {

        // Create a DateTimeFormatter with the specified pattern and locale
        val formatter = DateTimeFormatter.ofPattern(pattern, locale)

        // Parse the date string to java.time.LocalDateTime
        val javaLocalDateTime = JavaLocalDateTime.parse(dateString, formatter)

        // Define the desired time zone
        val zoneId = java.time.ZoneId.of(timeZone)

        // Convert java.time.LocalDateTime to java.time.ZonedDateTime in the specified time zone
        val zonedDateTime = javaLocalDateTime.atZone(zoneId)

        // Convert ZonedDateTime to epoch milliseconds
        return zonedDateTime.toInstant().toEpochMilli()
    }
}

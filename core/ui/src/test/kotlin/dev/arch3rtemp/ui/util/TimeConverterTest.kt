package dev.arch3rtemp.ui.util

import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals

class TimeConverterTest {

    private val timeConverter = TimeConverter()

    @Test
    fun test_convertLongToDateString() {
        val expected = "06 Jan 25"
        val result = timeConverter.convertLongToDateString(
            SIMULATED_CREATED_TIME,
            DATE_PATTERN,
            Locale.getDefault(),
            "Asia/Tbilisi"
        )

        assertEquals(expected, result)
    }

    companion object {
        const val DATE_PATTERN = "dd MMM yy"
        const val SIMULATED_CREATED_TIME = 1736190485327L
    }
}

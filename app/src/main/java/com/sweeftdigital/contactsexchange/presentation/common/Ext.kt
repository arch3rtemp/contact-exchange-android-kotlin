package com.sweeftdigital.contactsexchange.presentation.common

import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun dateToString(createDate: Date): String {
    val simpleDateFormat = SimpleDateFormat("dd MMM", Locale.US)
    return simpleDateFormat.format(createDate)
}

fun WindowManager.currentDeviceRealSize(): Pair<Int, Int> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        return Pair(
            maximumWindowMetrics.bounds.width(),
            maximumWindowMetrics.bounds.height())
    } else {
        val size = Point()
        @Suppress("DEPRECATION")
        defaultDisplay.getRealSize(size)
        Pair(size.x, size.y)
    }
}
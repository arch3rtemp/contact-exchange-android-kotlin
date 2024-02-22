package com.sweeftdigital.contactsexchange.presentation.common

import android.content.Context
import android.graphics.Point
import android.hardware.display.DisplayManager
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import androidx.core.view.DisplayCompat
import java.text.SimpleDateFormat
import java.util.*

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
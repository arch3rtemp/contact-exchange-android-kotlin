package dev.arch3rtemp.core_ui.util

import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun dateToString(date: Date): String {
    val simpleDateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
    return simpleDateFormat.format(date)
}

fun WindowManager.currentDeviceRealSize(): Pair<Int, Int> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Pair(
            currentWindowMetrics.bounds.width(),
            currentWindowMetrics.bounds.height())
    } else {
        val size = Point()
        @Suppress("DEPRECATION")
        defaultDisplay.getRealSize(size)
        Pair(size.x, size.y)
    }
}

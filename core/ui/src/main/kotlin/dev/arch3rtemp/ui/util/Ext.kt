package dev.arch3rtemp.ui.util

import android.graphics.Point
import android.os.Build
import android.view.WindowManager

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

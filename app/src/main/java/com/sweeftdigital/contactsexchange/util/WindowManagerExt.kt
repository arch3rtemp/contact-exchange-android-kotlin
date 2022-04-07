package com.sweeftdigital.contactsexchange.util

import android.graphics.Point
import android.os.Build
import android.view.WindowManager

fun WindowManager.currentDeviceRealSize(): Pair<Int, Int> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        return Pair(
            maximumWindowMetrics.bounds.width(),
            maximumWindowMetrics.bounds.height())
    } else {
        val size = Point()
        defaultDisplay.getRealSize(size)
        Pair(size.x, size.y)
    }
}
package dev.arch3rtemp.ui.util

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.annotation.ColorInt

object ColorUtils {

    fun createSrcInColorFilter(@ColorInt color: Int): PorterDuffColorFilter {
        return PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    }
}

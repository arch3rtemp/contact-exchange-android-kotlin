package dev.arch3rtemp.contactexchange.presentation.model

import android.graphics.PorterDuffColorFilter
import androidx.annotation.ColorInt
import dev.arch3rtemp.ui.util.ColorUtils
import dev.arch3rtemp.ui.view.listadapter.RvItem

data class CardUi(
    override val id: Int,
    val job: String,
    @ColorInt val color: Int
) : RvItem {

    fun getSrcInColorFilter(): PorterDuffColorFilter {
        return ColorUtils.createSrcInColorFilter(color)
    }
}

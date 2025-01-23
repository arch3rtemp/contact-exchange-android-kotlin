package dev.arch3rtemp.contactexchange.presentation.model

import android.graphics.PorterDuffColorFilter
import androidx.annotation.ColorInt
import kotlinx.serialization.Serializable
import dev.arch3rtemp.ui.util.ColorUtils
import dev.arch3rtemp.ui.view.listadapter.RvItem

data class ContactUi(
    override val id: Int,
    val name: String,
    val job: String,
    val position: String,
    val email: String,
    val phoneMobile: String,
    val phoneOffice: String,
    val createdAt: Long,
    val formattedCreatedAt: String,
    @ColorInt val color: Int,
    val isMy: Boolean
) : RvItem {

    fun getSrcInColorFilter(): PorterDuffColorFilter {
        return ColorUtils.createSrcInColorFilter(color)
    }

    fun formatInitials(): String {
        return if (name.contains(" ")) {
            val spaceIndex = name.indexOf(" ") + 1
            val firstLetter = name.substring(0, 1)
            val secondLetter = name.substring(spaceIndex, spaceIndex + 1)
            firstLetter + secondLetter
        } else {
            name.substring(0, 1)
        }
    }
}

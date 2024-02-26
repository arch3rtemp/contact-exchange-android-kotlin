package dev.arch3rtemp.core_ui.custom_view

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import dev.arch3rtemp.core_ui.R

class CustomSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.searchViewStyle
) : SearchView(context, attrs, defStyleAttr) {
    init {
        val searchTextView = findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        val customFont = ResourcesCompat.getFont(context, R.font.poppins_medium)
        val textColor = ResourcesCompat.getColor(resources, R.color.warm_grey_two,null)
        val hintColor = ResourcesCompat.getColor(resources, R.color.pinkish_grey, null)
        searchTextView.typeface = customFont
        searchTextView.textSize = 12f
        searchTextView.setTextColor(textColor)
        val closeIcon = findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        closeIcon.colorFilter = PorterDuffColorFilter(hintColor, PorterDuff.Mode.SRC_IN)
        val searchIcon = findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        val iconColor = ResourcesCompat.getColor(resources, R.color.black_two, null)
        val iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_search)
        iconDrawable?.colorFilter = PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN)
        searchIcon.setImageDrawable(iconDrawable)
    }
}
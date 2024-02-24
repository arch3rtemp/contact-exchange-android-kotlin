package com.sweeftdigital.contactsexchange.presentation.common

import android.content.Context
import androidx.annotation.StringRes

class StringResourceManager(private val context: Context) {
    fun string(@StringRes resId: Int): String {
        return context.getString(resId)
    }
}
package com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.ContactsListAdapter

interface ItemDrawer {
    fun createViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
    fun bind(vh: RecyclerView.ViewHolder, clickListener: ContactsListAdapter.ClickListener)
}
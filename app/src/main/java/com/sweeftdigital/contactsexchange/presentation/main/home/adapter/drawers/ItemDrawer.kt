package com.sweeftdigital.contactsexchange.presentation.main.home.adapter.drawers

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweeftdigital.contactsexchange.presentation.main.home.adapter.ContactsListAdapter

interface ItemDrawer {
    fun createViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
    fun bind(vh: RecyclerView.ViewHolder, clickListener: ContactsListAdapter.ClickListener)
}
package com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.ContactsListAdapter

interface ItemDrawer {
    val contact: Contact
    fun createViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
    fun bind(vh: RecyclerView.ViewHolder, clickListener: ContactsListAdapter.ClickListener)
}
package com.sweeftdigital.contactsexchange.presentation.home.adapter.drawer

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.presentation.home.adapter.ContactsListAdapter

interface ItemDrawer {
    val contact: Contact
    fun createViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
    fun bind(vh: RecyclerView.ViewHolder, clickListener: ContactsListAdapter.ClickListener)
}
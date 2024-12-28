package dev.arch3rtemp.contactexchange.presentation.home.adapter.drawer

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.presentation.home.adapter.ContactsListAdapter

interface ItemDrawer {
    val contact: Contact
    fun createViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
    fun bind(vh: RecyclerView.ViewHolder, clickListener: ContactsListAdapter.ClickListener)
}

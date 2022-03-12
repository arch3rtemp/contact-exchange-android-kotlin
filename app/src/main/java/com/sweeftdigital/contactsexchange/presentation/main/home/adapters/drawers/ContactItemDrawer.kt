package com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweeftdigital.contactsexchange.databinding.ContactListItemBinding
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.ContactsListAdapter

class ContactItemDrawer(override val contact: Contact) : ItemDrawer {
    private lateinit var binding: ContactListItemBinding

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        binding = ContactListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactsListAdapter.ContactsHolder(binding)
    }

    override fun bind(vh: RecyclerView.ViewHolder, clickListener: ContactsListAdapter.ClickListener) {
        (vh as ContactsListAdapter.ContactsHolder).apply {
            setData(contact, clickListener)
        }
    }
}
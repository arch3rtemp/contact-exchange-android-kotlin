package dev.arch3rtemp.contactexchange.presentation.home.adapter.drawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.arch3rtemp.contactexchange.databinding.ContactListItemBinding
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.presentation.home.adapter.ContactsListAdapter

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

package com.sweeftdigital.contactsexchange.presentation.main.home.adapter.drawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweeftdigital.contactsexchange.databinding.CardListItemBinding
import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.presentation.main.home.adapter.ContactsListAdapter

class CardItemDrawer(override val contact: Contact) : ItemDrawer {
    private lateinit var binding: CardListItemBinding

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        binding = CardListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactsListAdapter.CardsHolder(binding)
    }

    override fun bind(vh: RecyclerView.ViewHolder, clickListener: ContactsListAdapter.ClickListener) {
        (vh as ContactsListAdapter.CardsHolder).apply {
            setData(contact, clickListener)
        }
    }
}
package dev.arch3rtemp.contactexchange.presentation.home.adapter.drawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.arch3rtemp.contactexchange.databinding.CardListItemBinding
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.presentation.home.adapter.ContactsListAdapter

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

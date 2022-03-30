package com.sweeftdigital.contactsexchange.presentation.main.home.adapters

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.SparseArray
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sweeftdigital.contactsexchange.databinding.CardListItemBinding
import com.sweeftdigital.contactsexchange.databinding.ContactListItemBinding
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers.CardItemDrawer
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers.ContactItemDrawer
import com.sweeftdigital.contactsexchange.presentation.main.home.adapters.drawers.ItemDrawer
import com.sweeftdigital.contactsexchange.util.dateToString

val callback = object : DiffUtil.ItemCallback<ItemDrawer>() {
    override fun areItemsTheSame(oldItem: ItemDrawer, newItem: ItemDrawer): Boolean {
        return when {
            oldItem is ContactItemDrawer && newItem is ContactItemDrawer -> {
                oldItem.contact.id == newItem.contact.id
            }
            oldItem is CardItemDrawer && newItem is CardItemDrawer -> {
                oldItem.contact.id == newItem.contact.id
            }
            else -> {
                false
            }
        }
    }

    override fun areContentsTheSame(oldItem: ItemDrawer, newItem: ItemDrawer): Boolean {
        return oldItem != newItem
    }
}

class ContactsListAdapter(private val clickListener: ClickListener) :
    ListAdapter<ItemDrawer, RecyclerView.ViewHolder>(callback), Filterable {

    private val sparseArray = SparseArray<ItemDrawer>()
    private var unfilteredContacts = listOf<ItemDrawer>()
    private var filteredContacts = mutableListOf<ItemDrawer>()
    private var filtering = false

    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        val key = item.javaClass.hashCode()
        if (sparseArray.indexOfKey(key) == -1) {
            sparseArray.append(key, item)
        }
        return key
    }

    override fun onCreateViewHolder(parent: ViewGroup, key: Int): RecyclerView.ViewHolder {
        return sparseArray[key].createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        currentList[position].bind(holder, clickListener)
    }

    fun modifyList(list: List<ItemDrawer>) {
        unfilteredContacts = list
        submitList(list)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filtered = mutableListOf<ItemDrawer>()
                constraint?.let { constraint ->
                    if (constraint.isBlank()) {
                        filtered.addAll(unfilteredContacts)
                    } else {
                        val filterPattern = constraint.toString().lowercase().trim()
                        unfilteredContacts.forEach {
                            if (it.contact.name.lowercase().contains(filterPattern)) {
                                filtered.add(it)
                            }
                        }
                    }
                }
                val results = FilterResults().apply {
                    values = filtered
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                this@ContactsListAdapter.submitList(results?.values as List<ItemDrawer>)
            }
        }
    }

    fun filterContacts(sequence: CharSequence?) {
        if (sequence.isNullOrBlank()) {
            clearFilter()
            return
        }

        filtering = true
        val filterPattern = sequence.toString().lowercase().trim()
        val filtered = currentList.filter { contactDrawer ->
            contactDrawer.contact.name.lowercase().contains(filterPattern)
        } as MutableList<ItemDrawer>
        filteredContacts = filtered
        this.submitList(filteredContacts)
    }

    private fun clearFilter() {
        filtering = false
        this.submitList(unfilteredContacts)
    }

//    fun removeItem(position: Int) {
//        currentList.removeAt(position)
//    }
//
//    fun restoreItem(contact: Contact, position: Int) {
//        currentList.add(position, contact)
//    }

    interface ClickListener {
        fun onContactClicked(id: Int)
        fun onCardClicked(id: Int)
        fun onDeleteClicked(contact: Contact)
    }

    class ContactsHolder(private val binding: ContactListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(contact: Contact, clickListener: ClickListener) {
            with(binding) {
                tvContactName.text = contact.name
                tvContactInitials.text = formatInitials(contact.name)
                tvContactPosition.text = contact.position
                tvContactAddDate.text = contact.dateToString()
                llItemRoot.setOnClickListener { clickListener.onContactClicked(contact.id) }
                llDelete.setOnClickListener {
                    root.close(true)
                    clickListener.onDeleteClicked(contact)
                }
                llContactInitials.background.colorFilter = PorterDuffColorFilter(
                    contact.color,
                    PorterDuff.Mode.SRC_IN
                )
            }
        }

        private fun formatInitials(name: String) =
            if (name.contains(" ")) {
                val spaceIndex = name.indexOf(" ") + 1
                String.format(
                    "${name.substring(0, 1)}${
                        name.substring(
                            spaceIndex,
                            spaceIndex + 1
                        )
                    }"
                )
            } else {
                name.substring(0, 1)
            }
    }

    class CardsHolder(private val binding: CardListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(contact: Contact, clickListener: ClickListener) {
            with(binding) {
                tvCard.text = contact.job
                tvCard.background.colorFilter = PorterDuffColorFilter(
                    contact.color,
                    PorterDuff.Mode.SRC_IN
                )
                tvCard.setOnClickListener { clickListener.onCardClicked(contact.id) }
            }
        }
    }
}
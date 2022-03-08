package com.sweeftdigital.contactsexchange.presentation.main.home.adapters

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.SparseArray
import android.view.ViewGroup
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
    ListAdapter<ItemDrawer, RecyclerView.ViewHolder>(callback) {

    private val sparseArray = SparseArray<ItemDrawer>()


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
        fun onDeleteClicked(id: Int)
    }

    class ContactsHolder(private val binding: ContactListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(contact: Contact, clickListener: ClickListener) {
            with(binding) {
                tvContactName.text = contact.name
                if (contact.name.contains(" ")) {
                    val spaceIndex = contact.name.indexOf(" ") + 1
                    tvContactInitials.text = String.format(
                        "${contact.name.substring(0, 1)}${
                            contact.name.substring(
                                spaceIndex,
                                spaceIndex + 1
                            )
                        }"
                    )
                } else {
                    tvContactInitials.text = contact.name.substring(0, 1)
                }
                tvContactPosition.text = contact.position
                tvContactAddDate.text = contact.dateToString()
                llItemRoot.setOnClickListener { clickListener.onContactClicked(contact.id) }
                llDelete.setOnClickListener { clickListener.onDeleteClicked(contact.id) }
                llContactInitials.background.colorFilter = PorterDuffColorFilter(
                    contact.color,
                    PorterDuff.Mode.SRC_IN
                )
            }
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
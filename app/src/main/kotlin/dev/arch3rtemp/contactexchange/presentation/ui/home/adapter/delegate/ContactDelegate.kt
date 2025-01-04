package dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.databinding.ContactListItemBinding
import dev.arch3rtemp.contactexchange.presentation.model.ContactUi
import dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.listener.ContactClickListener
import dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.viewholder.ContactViewHolder
import dev.arch3rtemp.ui.view.listadapter.DefaultViewHolder
import dev.arch3rtemp.ui.view.listadapter.RvDelegate
import dev.arch3rtemp.ui.view.listadapter.RvItem

class ContactDelegate(
    private val listener: ContactClickListener
) : RvDelegate<ContactListItemBinding, ContactUi> {

    override fun isRelevantItem(item: RvItem): Boolean {
        return item is ContactUi
    }

    override fun layoutResId(): Int {
        return R.layout.contact_list_item
    }

    override fun viewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): DefaultViewHolder<ContactListItemBinding, ContactUi> {
        return ContactViewHolder(ContactListItemBinding.inflate(layoutInflater, parent, false), listener)
    }
}

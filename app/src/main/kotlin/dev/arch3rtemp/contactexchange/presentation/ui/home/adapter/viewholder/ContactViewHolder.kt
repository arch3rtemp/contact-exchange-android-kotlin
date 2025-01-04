package dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.viewholder

import dev.arch3rtemp.contactexchange.databinding.ContactListItemBinding
import dev.arch3rtemp.contactexchange.presentation.model.ContactUi
import dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.listener.ContactClickListener
import dev.arch3rtemp.ui.view.listadapter.DefaultViewHolder

class ContactViewHolder(
    binding: ContactListItemBinding,
    private val listener: ContactClickListener
) : DefaultViewHolder<ContactListItemBinding, ContactUi>(binding) {

    override fun onBind(item: ContactUi) {
        super.onBind(item)

        binding.apply {
            tvContactName.text = item.name
            tvContactInitials.text = item.formatInitials()
            tvContactPosition.text = item.position
            tvContactAddDate.text = item.formattedCreatedAt
            llContactInitials.background.colorFilter = item.getSrcInColorFilter()

            llItemRoot.setOnClickListener {
                root.close(true)
                listener.onContactClick(item.id)
            }

            llDelete.setOnClickListener {
                root.close(true)
                listener.onDeleteClick(item)
            }
        }
    }
}

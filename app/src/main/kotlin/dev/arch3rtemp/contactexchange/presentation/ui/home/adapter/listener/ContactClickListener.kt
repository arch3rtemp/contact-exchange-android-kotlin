package dev.arch3rtemp.contactexchange.presentation.ui.home.adapter.listener

import dev.arch3rtemp.contactexchange.presentation.model.ContactUi

interface ContactClickListener {
    fun onContactClick(id: Int)
    fun onDeleteClick(contact: ContactUi)
}

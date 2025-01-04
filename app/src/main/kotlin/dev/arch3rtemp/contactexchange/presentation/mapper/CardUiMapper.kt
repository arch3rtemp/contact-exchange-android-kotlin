package dev.arch3rtemp.contactexchange.presentation.mapper

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.presentation.model.CardUi

class CardUiMapper() {

    fun toUiModel(contact: Contact): CardUi {
        return CardUi(
            id = contact.id,
            job = contact.job,
            color = contact.color
        )
    }

    fun toUiList(contacts: List<Contact>): List<CardUi> {
        return contacts.map { contact ->
            toUiModel(contact)
        }
    }
}

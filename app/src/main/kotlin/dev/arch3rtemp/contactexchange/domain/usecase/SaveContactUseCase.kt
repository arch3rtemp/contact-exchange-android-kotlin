package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.CardRepository

class SaveContactUseCase(private val repo: CardRepository) {

    suspend operator fun invoke(contact: Contact) {
        return repo.addContact(contact)
    }
}

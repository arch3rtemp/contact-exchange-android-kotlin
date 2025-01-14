package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.ContactRepository

class SaveContactUseCase(private val repo: ContactRepository) {

    suspend operator fun invoke(contact: Contact) {
        return repo.addContact(contact)
    }
}

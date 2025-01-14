package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.ContactRepository

class UpdateContactUseCase(private val repo: ContactRepository) {

    suspend operator fun invoke(current: Contact, newCard: Contact) {
        return repo.updateContact(mergeContact(current, newCard))
    }

    private fun mergeContact(current: Contact, newCard: Contact): Contact {
        return current.copy(
            name = newCard.name,
            job = newCard.job,
            position = newCard.position,
            email = newCard.email,
            phoneMobile = newCard.phoneMobile,
            phoneOffice = newCard.phoneOffice,
            createdAt = newCard.createdAt,
        )
    }
}

package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.CardRepository

class UpdateContactUseCase(private val repo: CardRepository) {

    suspend operator fun invoke(current: Contact, newCard: Contact) {
        return repo.updateContact(mergeContact(current, newCard))
    }

    private fun mergeContact(current: Contact, newCard: Contact): Contact {
        return Contact(
            current.id,
            newCard.name,
            newCard.job,
            newCard.position,
            newCard.email,
            newCard.phoneMobile,
            newCard.phoneOffice,
            newCard.createdAt,
            current.color,
            current.isMy
        )
    }
}

package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.model.Contact

class ValidateCardUseCase {

    operator fun invoke(contact: Contact): Boolean {
        return contact.run { name.isNotBlank() && job.isNotBlank() && position.isNotBlank() && email.isNotBlank() && phoneMobile.isNotBlank() && phoneOffice.isNotBlank() }
    }
}

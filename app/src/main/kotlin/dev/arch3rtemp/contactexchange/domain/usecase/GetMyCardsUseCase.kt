package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow

class GetMyCardsUseCase(private val repo: ContactRepository) {

    suspend operator fun invoke(): Flow<List<Contact>> {
        return repo.getMyContacts()
    }
}

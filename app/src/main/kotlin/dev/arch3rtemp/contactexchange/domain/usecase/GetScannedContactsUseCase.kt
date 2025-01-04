package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow

class GetScannedContactsUseCase(private val repo: CardRepository) {

    suspend operator fun invoke(): Flow<List<Contact>> {
        return repo.selectAllScannedContacts()
    }
}

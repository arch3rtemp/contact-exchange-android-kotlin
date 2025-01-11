package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow

class GetMyCardsUseCase(private val repo: CardRepository) {

    suspend fun invoke(): Flow<List<Contact>> {
        return repo.selectAllMyContacts()
    }
}

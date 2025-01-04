package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.CardRepository

class GetContactByIdUseCase(private val repo: CardRepository) {

    suspend operator fun invoke(id: Int): Contact {
        return repo.selectContactById(id)
    }
}

package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.repository.CardRepository

class DeleteContactUseCase(private val repo: CardRepository) {

    suspend operator fun invoke(id: Int) {
        return repo.deleteContact(id)
    }
}

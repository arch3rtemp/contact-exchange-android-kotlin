package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.repository.CardRepository
import dev.arch3rtemp.ui.R
import dev.arch3rtemp.ui.util.StringResourceManager

class DeleteContactUseCase(
    private val repo: CardRepository,
    private val stringManager: StringResourceManager
) {

    suspend operator fun invoke(id: Int) {
        if (id <= 0) {
            throw IllegalArgumentException(stringManager.string(R.string.msg_id_must_be_positive))
        }
        return repo.deleteContact(id)
    }
}

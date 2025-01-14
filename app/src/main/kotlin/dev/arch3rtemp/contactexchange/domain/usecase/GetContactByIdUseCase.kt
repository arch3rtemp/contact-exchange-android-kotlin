package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.ContactRepository
import dev.arch3rtemp.ui.R
import dev.arch3rtemp.ui.util.StringResourceManager

class GetContactByIdUseCase(
    private val repo: ContactRepository,
    private val stringManager: StringResourceManager
) {

    suspend operator fun invoke(id: Int): Contact {
        if (id <= 0) {
            throw IllegalArgumentException(stringManager.string(R.string.msg_id_must_be_positive))
        }
        return repo.getContactById(id)
    }
}

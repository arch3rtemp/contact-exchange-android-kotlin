package dev.arch3rtemp.contactexchange.domain.use_case

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.Repository
import dev.arch3rtemp.contactexchange.domain.use_case.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class SaveContactUseCase(private val repo: Repository) : BaseUseCase<Contact, Flow<Unit>> {
    override suspend fun start(arg: Contact?): Flow<Unit> {
        return repo.addContact(arg!!)
    }
}

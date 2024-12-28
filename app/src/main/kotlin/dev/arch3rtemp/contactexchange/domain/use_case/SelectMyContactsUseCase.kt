package dev.arch3rtemp.contactexchange.domain.use_case

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.Repository
import dev.arch3rtemp.contactexchange.domain.use_case.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class SelectMyContactsUseCase(private val repo: Repository) : BaseUseCase<Unit, Flow<List<Contact>>> {
    override suspend fun start(arg: Unit?): Flow<List<Contact>> {
        return repo.selectAllMyContacts()
    }
}

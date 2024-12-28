package dev.arch3rtemp.contactexchange.domain.use_case

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.Repository
import dev.arch3rtemp.contactexchange.domain.use_case.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class SelectContactByIdUseCase(private val repo: Repository) : BaseUseCase<Int, Flow<Contact>> {
    override suspend fun start(arg: Int?): Flow<Contact> {
        return repo.selectContactById(arg!!)
    }
}

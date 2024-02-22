package com.sweeftdigital.contactsexchange.domain.use_case

import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.domain.repository.Repository
import com.sweeftdigital.contactsexchange.domain.use_case.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class SelectMyContactsUseCase(private val repo: Repository) : BaseUseCase<Unit, Flow<List<Contact>>> {
    override suspend fun start(arg: Unit?): Flow<List<Contact>> {
        return repo.selectAllMyContacts()
    }
}
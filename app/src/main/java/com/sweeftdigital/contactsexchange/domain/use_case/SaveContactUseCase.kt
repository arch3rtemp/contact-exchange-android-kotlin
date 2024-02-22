package com.sweeftdigital.contactsexchange.domain.use_case

import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.domain.repository.Repository
import com.sweeftdigital.contactsexchange.domain.use_case.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class SaveContactUseCase(private val repo: Repository) : BaseUseCase<Contact, Flow<Unit>> {
    override suspend fun start(arg: Contact?): Flow<Unit> {
        return repo.addContact(arg!!)
    }
}
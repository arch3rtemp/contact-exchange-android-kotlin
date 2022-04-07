package com.sweeftdigital.contactsexchange.domain.useCases

import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.repository.Repository
import com.sweeftdigital.contactsexchange.domain.useCases.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class UpdateContactUseCase(private val repo: Repository) : BaseUseCase<Contact, Flow<Unit>> {
    override suspend fun start(arg: Contact?): Flow<Unit> {
        return repo.updateContact(arg!!)
    }
}
package com.sweeftdigital.contactsexchange.domain.useCases

import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.repository.Repository
import com.sweeftdigital.contactsexchange.domain.useCases.base.BaseUseCase

class SaveContactUseCase(private val repo: Repository) : BaseUseCase<Contact, Unit> {
    override suspend fun start(arg: Contact?) {
        repo.addContact(arg!!)
    }
}
package com.sweeftdigital.contactsexchange.domain.use_case

import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.domain.repository.Repository
import com.sweeftdigital.contactsexchange.domain.use_case.base.BaseUseCase

class SelectAllContactsUseCase(private val repo: Repository) : BaseUseCase<Unit, List<Contact>> {
    override suspend fun start(arg: Unit?): List<Contact> {
        return repo.selectAllContacts()
    }
}
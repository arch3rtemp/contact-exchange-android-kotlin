package com.sweeftdigital.contactsexchange.domain.useCases

import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.repository.Repository
import com.sweeftdigital.contactsexchange.domain.useCases.base.BaseUseCase

class SelectMyContactsUseCase(private val repo: Repository) : BaseUseCase<Unit, List<Contact>> {
    override fun start(arg: Unit?): List<Contact> {
        return repo.selectAllMyContacts()
    }
}
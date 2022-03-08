package com.sweeftdigital.contactsexchange.domain.useCases

import androidx.lifecycle.LiveData
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.repository.Repository
import com.sweeftdigital.contactsexchange.domain.useCases.base.BaseUseCase

class SelectMyContactsUseCase(private val repo: Repository) : BaseUseCase<Unit, LiveData<List<Contact>>> {
    override suspend fun start(arg: Unit?): LiveData<List<Contact>> {
        return repo.selectAllMyContacts()
    }
}
package com.sweeftdigital.contactsexchange.domain.repository

import androidx.lifecycle.LiveData
import com.sweeftdigital.contactsexchange.domain.data_providers.local.LocalDataProvider
import com.sweeftdigital.contactsexchange.domain.models.Contact

class RepositoryImpl(
    private val localDataProvider: LocalDataProvider
) : Repository {
    override suspend fun selectAllMyContacts(): LiveData<List<Contact>> {
        return localDataProvider.selectAllMyContacts()
    }

    override suspend fun selectAllScannedContacts(): LiveData<List<Contact>> {
        return localDataProvider.selectAllScannedContacts()
    }

    override suspend fun selectAllContacts(): List<Contact> {
        return localDataProvider.selectAllContacts()
    }

    override suspend fun addContact(contact: Contact) {
        return localDataProvider.addContact(contact)
    }

    override suspend fun selectContactById(id: Int): Contact {
        return localDataProvider.selectContactById(id)
    }

    override suspend fun deleteContact(id: Int) {
        return localDataProvider.deleteContact(id)
    }

}
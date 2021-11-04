package com.sweeftdigital.contactsexchange.domain.repository

import com.sweeftdigital.contactsexchange.util.toContact
import com.sweeftdigital.contactsexchange.util.toContactEntity
import com.sweeftdigital.contactsexchange.domain.data_providers.local.LocalDataProvider
import com.sweeftdigital.contactsexchange.domain.models.Contact

class RepositoryImpl(
    private val localDataProvider: LocalDataProvider
) : Repository {
    override fun selectAllMyContacts(): List<Contact> {
        return localDataProvider.selectAllMyContacts().map { it.toContact() }
    }

    override fun selectAllScannedContacts(): List<Contact> {
        return localDataProvider.selectAllScannedContacts().map { it.toContact() }
    }

    override fun addContact(contact: Contact) {
        return localDataProvider.addContact(contact.toContactEntity())
    }

    override fun selectContactById(id: Int): Contact {
        return localDataProvider.selectContactById(id).toContact()
    }

    override fun deleteContact(id: Int) {
        return localDataProvider.deleteContact(id)
    }
}
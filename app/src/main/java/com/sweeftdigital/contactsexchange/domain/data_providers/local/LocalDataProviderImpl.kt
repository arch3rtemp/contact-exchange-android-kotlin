package com.sweeftdigital.contactsexchange.domain.data_providers.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sweeftdigital.contactsexchange.app.db.AppDatabase
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.util.toContact
import com.sweeftdigital.contactsexchange.util.toContactEntity

class LocalDataProviderImpl(private val database: AppDatabase) : LocalDataProvider {
    override suspend fun selectAllMyContacts(): LiveData<List<Contact>> {
        return Transformations.map(database.contactDao().selectAllMyContacts()) { list ->
            list.map { it.toContact() }
        }
    }

    override suspend fun selectAllScannedContacts(): LiveData<List<Contact>> {
        return Transformations.map(database.contactDao().selectAllScannedContacts()) { list ->
            list.map { it.toContact() }
        }
    }

    override suspend fun selectAllContacts(): List<Contact> {
        return database.contactDao().selectAllContacts().map { it.toContact() }
    }

    override suspend fun addContact(contact: Contact) {
        database.contactDao().insert(contact.toContactEntity())
    }

    override suspend fun selectContactById(id: Int): Contact {
        return database.contactDao().selectContactById(id).toContact()
    }

    override suspend fun deleteContact(id: Int) {
        database.contactDao().delete(id)
    }
}
package com.sweeftdigital.contactsexchange.domain.data_providers.local

import com.sweeftdigital.contactsexchange.data.db.AppDatabase
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.util.toContact
import com.sweeftdigital.contactsexchange.util.toContactEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataProviderImpl(private val database: AppDatabase) : LocalDataProvider {
    override suspend fun selectAllMyContacts(): Flow<List<Contact>> {
        return database.contactDao().selectAllMyContacts().map { list ->
            list.map {
                it.toContact()
            }
        }
    }

    override suspend fun selectAllScannedContacts(): Flow<List<Contact>> {
        return database.contactDao().selectAllScannedContacts().map { list ->
            list.map {
                it.toContact()
            }
        }
    }

    override suspend fun selectAllContacts(): List<Contact> {
        return database.contactDao().selectAllContacts().map { it.toContact() }
    }

    override suspend fun addContact(contact: Contact) {
        database.contactDao().insert(contact.toContactEntity())
    }

    override suspend fun updateContact(contact: Contact) {
        database.contactDao().update(contact.toContactEntity())
    }

    override suspend fun selectContactById(id: Int): Contact {
        return database.contactDao().selectContactById(id).toContact()
    }

    override suspend fun deleteContact(id: Int) {
        database.contactDao().delete(id)
    }
}
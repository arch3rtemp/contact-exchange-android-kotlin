package com.sweeftdigital.contactsexchange.data.providers.local

import com.sweeftdigital.contactsexchange.data.db.AppDatabase
import com.sweeftdigital.contactsexchange.data.mapper.contactToEntity
import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.data.mapper.entityToContact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataProviderImpl(private val database: AppDatabase) : LocalDataProvider {
    override suspend fun selectAllMyContacts(): Flow<List<Contact>> {
        return database.contactDao().selectAllMyContacts().map { list ->
            list.map {
                entityToContact(it)
            }
        }
    }

    override suspend fun selectAllScannedContacts(): Flow<List<Contact>> {
        return database.contactDao().selectAllScannedContacts().map { list ->
            list.map {
                entityToContact(it)
            }
        }
    }

    override suspend fun selectAllContacts(): List<Contact> {
        return database.contactDao().selectAllContacts().map { entityToContact(it) }
    }

    override suspend fun addContact(contact: Contact) {
        database.contactDao().insert(contactToEntity(contact))
    }

    override suspend fun updateContact(contact: Contact) {
        database.contactDao().update(contactToEntity(contact))
    }

    override suspend fun selectContactById(id: Int): Contact {
        return entityToContact(database.contactDao().selectContactById(id))
    }

    override suspend fun deleteContact(id: Int) {
        database.contactDao().delete(id)
    }
}
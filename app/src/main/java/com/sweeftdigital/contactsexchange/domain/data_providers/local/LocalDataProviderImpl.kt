package com.sweeftdigital.contactsexchange.domain.data_providers.local

import com.sweeftdigital.contactsexchange.app.db.AppDatabase
import com.sweeftdigital.contactsexchange.app.db.models.ContactEntity

class LocalDataProviderImpl(private val database: AppDatabase) : LocalDataProvider {
    override suspend fun selectAllMyContacts(): List<ContactEntity> {
        return database.contactDao().selectAllMyContacts()
    }

    override suspend fun selectAllScannedContacts(): List<ContactEntity> {
        return database.contactDao().selectAllScannedContacts()
    }

    override suspend fun addContact(contact: ContactEntity) {
        database.contactDao().insert(contact)
    }

    override suspend fun selectContactById(id: Int): ContactEntity {
        return database.contactDao().selectContactById(id)
    }

    override suspend fun deleteContact(id: Int) {
        database.contactDao().delete(id)
    }
}
package com.sweeftdigital.contactsexchange.domain.data_providers.local

import com.sweeftdigital.contactsexchange.app.db.AppDatabase
import com.sweeftdigital.contactsexchange.app.db.models.ContactEntity

class LocalDataProviderImpl(private val database: AppDatabase) : LocalDataProvider {
    override fun selectAllMyContacts(): List<ContactEntity> {
        return database.contactDao().selectAllMyContacts()
    }

    override fun selectAllScannedContacts(): List<ContactEntity> {
        return database.contactDao().selectAllScannedContacts()
    }

    override fun addContact(contact: ContactEntity) {
        database.contactDao().insert(contact)
    }

    override fun selectContactById(id: Int): ContactEntity {
        return database.contactDao().selectContactById(id)
    }

    override fun deleteContact(id: Int) {
        database.contactDao().delete(id)
    }
}
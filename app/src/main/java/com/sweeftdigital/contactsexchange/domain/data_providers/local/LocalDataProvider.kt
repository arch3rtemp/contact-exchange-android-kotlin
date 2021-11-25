package com.sweeftdigital.contactsexchange.domain.data_providers.local

import com.sweeftdigital.contactsexchange.app.db.models.ContactEntity

interface LocalDataProvider {
    suspend fun selectAllMyContacts(): List<ContactEntity>
    suspend fun selectAllScannedContacts(): List<ContactEntity>
    suspend fun addContact(contact: ContactEntity)
    suspend fun selectContactById(id: Int): ContactEntity
    suspend fun deleteContact(id: Int)
}
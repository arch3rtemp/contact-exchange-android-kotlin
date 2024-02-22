package com.sweeftdigital.contactsexchange.data.providers.local


import com.sweeftdigital.contactsexchange.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface LocalDataProvider {
    suspend fun selectAllMyContacts(): Flow<List<Contact>>
    suspend fun selectAllScannedContacts(): Flow<List<Contact>>
    suspend fun selectAllContacts(): List<Contact>
    suspend fun addContact(contact: Contact)
    suspend fun updateContact(contact: Contact)
    suspend fun selectContactById(id: Int): Contact
    suspend fun deleteContact(id: Int)
}
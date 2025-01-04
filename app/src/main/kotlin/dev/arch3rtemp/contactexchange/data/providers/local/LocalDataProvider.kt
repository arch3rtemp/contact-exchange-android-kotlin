package dev.arch3rtemp.contactexchange.data.providers.local

import dev.arch3rtemp.contactexchange.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface LocalDataProvider {
    suspend fun selectAllMyContacts(): Flow<List<Contact>>
    suspend fun selectAllScannedContacts(): Flow<List<Contact>>
    suspend fun selectContactById(id: Int): Contact
    suspend fun addContact(contact: Contact)
    suspend fun updateContact(contact: Contact)
    suspend fun deleteContact(id: Int)
}

package dev.arch3rtemp.contactexchange.domain.repository

import dev.arch3rtemp.contactexchange.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun getMyContacts(): Flow<List<Contact>>
    suspend fun getScannedContacts(): Flow<List<Contact>>
    suspend fun getContactById(id: Int): Contact
    suspend fun addContact(contact: Contact)
    suspend fun updateContact(contact: Contact)
    suspend fun deleteContact(id: Int)
}

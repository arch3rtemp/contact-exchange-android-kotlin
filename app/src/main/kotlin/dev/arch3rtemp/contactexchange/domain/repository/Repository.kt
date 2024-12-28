package dev.arch3rtemp.contactexchange.domain.repository

import dev.arch3rtemp.contactexchange.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun selectAllMyContacts(): Flow<List<Contact>> // TODO Replace with word Card
    suspend fun selectAllScannedContacts(): Flow<List<Contact>>
    suspend fun addContact(contact: Contact): Flow<Unit>
    suspend fun updateContact(contact: Contact): Flow<Unit>
    suspend fun selectContactById(id: Int): Flow<Contact>
    suspend fun deleteContact(id: Int): Flow<Unit>
}

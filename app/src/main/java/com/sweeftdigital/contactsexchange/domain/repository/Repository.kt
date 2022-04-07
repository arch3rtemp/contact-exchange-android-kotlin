package com.sweeftdigital.contactsexchange.domain.repository

import androidx.lifecycle.LiveData
import com.sweeftdigital.contactsexchange.domain.models.Contact
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun selectAllMyContacts(): Flow<List<Contact>>
    suspend fun selectAllScannedContacts(): Flow<List<Contact>>
    suspend fun selectAllContacts(): List<Contact>
    suspend fun addContact(contact: Contact): Flow<Unit>
    suspend fun updateContact(contact: Contact): Flow<Unit>
    suspend fun selectContactById(id: Int): Flow<Contact>
    suspend fun deleteContact(id: Int): Flow<Unit>
}
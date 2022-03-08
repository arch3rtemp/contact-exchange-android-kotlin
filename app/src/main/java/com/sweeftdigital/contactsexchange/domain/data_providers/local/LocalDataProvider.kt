package com.sweeftdigital.contactsexchange.domain.data_providers.local

import androidx.lifecycle.LiveData
import com.sweeftdigital.contactsexchange.domain.models.Contact

interface LocalDataProvider {
    suspend fun selectAllMyContacts(): LiveData<List<Contact>>
    suspend fun selectAllScannedContacts(): LiveData<List<Contact>>
    suspend fun selectAllContacts(): List<Contact>
    suspend fun addContact(contact: Contact)
    suspend fun selectContactById(id: Int): Contact
    suspend fun deleteContact(id: Int)
}
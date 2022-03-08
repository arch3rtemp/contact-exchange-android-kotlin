package com.sweeftdigital.contactsexchange.domain.repository

import androidx.lifecycle.LiveData
import com.sweeftdigital.contactsexchange.domain.models.Contact

interface Repository {
    suspend fun selectAllMyContacts(): LiveData<List<Contact>>
    suspend fun selectAllScannedContacts(): LiveData<List<Contact>>
    suspend fun selectAllContacts(): List<Contact>
    suspend fun addContact(contact: Contact)
    suspend fun selectContactById(id: Int): Contact
    suspend fun deleteContact(id: Int)
}
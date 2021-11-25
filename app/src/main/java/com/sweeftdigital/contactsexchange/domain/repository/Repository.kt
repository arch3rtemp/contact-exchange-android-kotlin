package com.sweeftdigital.contactsexchange.domain.repository

import com.sweeftdigital.contactsexchange.domain.models.Contact

interface Repository {
    suspend fun selectAllMyContacts(): List<Contact>
    suspend fun selectAllScannedContacts(): List<Contact>
    suspend fun addContact(contact: Contact)
    suspend fun selectContactById(id: Int): Contact
    suspend fun deleteContact(id: Int)
}
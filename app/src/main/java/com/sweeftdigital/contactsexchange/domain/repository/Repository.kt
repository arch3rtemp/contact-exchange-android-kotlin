package com.sweeftdigital.contactsexchange.domain.repository

import com.sweeftdigital.contactsexchange.domain.models.Contact

interface Repository {
    fun selectAllMyContacts(): List<Contact>
    fun selectAllScannedContacts(): List<Contact>
    fun addContact(contact: Contact)
    fun selectContactById(id: Int): Contact
    fun deleteContact(id: Int)
}
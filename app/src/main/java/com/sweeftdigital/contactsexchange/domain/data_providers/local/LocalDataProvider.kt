package com.sweeftdigital.contactsexchange.domain.data_providers.local

import com.sweeftdigital.contactsexchange.app.db.models.ContactEntity

interface LocalDataProvider {
    fun selectAllMyContacts(): List<ContactEntity>
    fun selectAllScannedContacts(): List<ContactEntity>
    fun addContact(contact: ContactEntity)
    fun selectContactById(id: Int): ContactEntity
    fun deleteContact(id: Int)
}
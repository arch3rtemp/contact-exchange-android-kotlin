package dev.arch3rtemp.contactexchange.data.repository

import dev.arch3rtemp.contactexchange.data.dataprovider.local.LocalDataProvider
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow

class ContactRepositoryImpl(private val localDataProvider: LocalDataProvider) : ContactRepository {

    override suspend fun getMyContacts(): Flow<List<Contact>> {
        return localDataProvider.selectMyContacts()
    }

    override suspend fun getScannedContacts(): Flow<List<Contact>> {
        return localDataProvider.selectScannedContacts()
    }

    override suspend fun getContactById(id: Int): Contact {
        return localDataProvider.selectContactById(id)
    }

    override suspend fun addContact(contact: Contact) {
        return localDataProvider.addContact(contact)
    }

    override suspend fun updateContact(contact: Contact) {
        return localDataProvider.updateContact(contact)
    }

    override suspend fun deleteContact(id: Int) {
        return localDataProvider.deleteContact(id)
    }

}

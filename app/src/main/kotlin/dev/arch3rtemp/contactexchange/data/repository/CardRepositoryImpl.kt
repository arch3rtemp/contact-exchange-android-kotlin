package dev.arch3rtemp.contactexchange.data.repository

import dev.arch3rtemp.contactexchange.data.providers.local.LocalDataProvider
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow

class CardRepositoryImpl(private val localDataProvider: LocalDataProvider) : CardRepository {

    override suspend fun selectAllMyContacts(): Flow<List<Contact>> {
        return localDataProvider.selectAllMyContacts()
    }

    override suspend fun selectAllScannedContacts(): Flow<List<Contact>> {
        return localDataProvider.selectAllScannedContacts()
    }

    override suspend fun selectContactById(id: Int): Contact {
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

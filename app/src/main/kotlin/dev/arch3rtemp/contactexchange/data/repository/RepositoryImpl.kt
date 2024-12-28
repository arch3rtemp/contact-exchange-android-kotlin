package dev.arch3rtemp.contactexchange.data.repository

import dev.arch3rtemp.contactexchange.data.providers.local.LocalDataProvider
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(
    private val localDataProvider: LocalDataProvider
) : Repository {
    override suspend fun selectAllMyContacts(): Flow<List<Contact>> {
        return localDataProvider.selectAllMyContacts()
    }

    override suspend fun selectAllScannedContacts(): Flow<List<Contact>> {
        return localDataProvider.selectAllScannedContacts()
    }

    override suspend fun addContact(contact: Contact): Flow<Unit> {
        return flow {
            emit(localDataProvider.addContact(contact))
        }
    }

    override suspend fun updateContact(contact: Contact): Flow<Unit> {
        return flow {
            emit(localDataProvider.updateContact(contact))
        }
    }

    override suspend fun selectContactById(id: Int): Flow<Contact> {
        return localDataProvider.selectContactById(id)
    }

    override suspend fun deleteContact(id: Int): Flow<Unit> {
        return flow {
            emit(localDataProvider.deleteContact(id))
        }
    }

}

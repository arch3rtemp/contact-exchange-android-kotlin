package com.sweeftdigital.contactsexchange.data.repository

import com.sweeftdigital.contactsexchange.data.providers.local.LocalDataProvider
import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.domain.repository.Repository
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

    override suspend fun selectAllContacts(): List<Contact> {
        return localDataProvider.selectAllContacts()
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
        return flow {
            emit(localDataProvider.selectContactById(id))
        }
    }

    override suspend fun deleteContact(id: Int): Flow<Unit> {
        return flow {
            emit(localDataProvider.deleteContact(id))
        }
    }

}
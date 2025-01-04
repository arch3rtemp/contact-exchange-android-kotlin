package dev.arch3rtemp.contactexchange.data.providers.local

import dev.arch3rtemp.contactexchange.data.db.AppDatabase
import dev.arch3rtemp.contactexchange.data.mapper.ContactEntityMapper
import dev.arch3rtemp.contactexchange.domain.model.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataProviderImpl(
    private val database: AppDatabase,
    private val mapper: ContactEntityMapper
) : LocalDataProvider {

    override suspend fun selectAllMyContacts(): Flow<List<Contact>> {
        return database.contactDao().selectAllMyContacts().map(mapper::fromEntityList)
    }

    override suspend fun selectAllScannedContacts(): Flow<List<Contact>> {
        return database.contactDao().selectAllScannedContacts().map(mapper::fromEntityList)
    }

    override suspend fun selectContactById(id: Int): Contact {
        return mapper.fromEntity(database.contactDao().selectContactById(id))
    }

    override suspend fun addContact(contact: Contact) {
        return database.contactDao().insert(mapper.toEntity(contact))
    }

    override suspend fun updateContact(contact: Contact) {
        return database.contactDao().update(mapper.toEntity(contact))
    }

    override suspend fun deleteContact(id: Int) {
        return database.contactDao().delete(id)
    }
}

package dev.arch3rtemp.contactexchange.data.providers.local

import dev.arch3rtemp.contactexchange.data.db.AppDatabase
import dev.arch3rtemp.contactexchange.data.mapper.contactToEntity
import dev.arch3rtemp.contactexchange.data.mapper.entityToContact
import dev.arch3rtemp.contactexchange.domain.model.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataProviderImpl(private val database: AppDatabase) : LocalDataProvider {
    override suspend fun selectAllMyContacts(): Flow<List<Contact>> {
        return database.contactDao().selectAllMyContacts().map { list ->
            list.map {
                entityToContact(it)
            }
        }
    }

    override suspend fun selectAllScannedContacts(): Flow<List<Contact>> {
        return database.contactDao().selectAllScannedContacts().map { list ->
            list.map {
                entityToContact(it)
            }
        }
    }

    override suspend fun addContact(contact: Contact) {
        database.contactDao().insert(contactToEntity(contact))
    }

    override suspend fun updateContact(contact: Contact) {
        database.contactDao().update(contactToEntity(contact))
    }

    override suspend fun selectContactById(id: Int): Flow<Contact> {
        return database.contactDao().selectContactById(id).map { contact ->
            entityToContact(contact)
        }
    }

    override suspend fun deleteContact(id: Int) {
        database.contactDao().delete(id)
    }
}

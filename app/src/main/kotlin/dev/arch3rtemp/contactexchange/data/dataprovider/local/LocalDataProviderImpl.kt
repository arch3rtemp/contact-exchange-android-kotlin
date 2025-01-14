package dev.arch3rtemp.contactexchange.data.dataprovider.local

import dev.arch3rtemp.contactexchange.data.db.dao.ContactDao
import dev.arch3rtemp.contactexchange.data.mapper.ContactEntityMapper
import dev.arch3rtemp.contactexchange.domain.model.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataProviderImpl(
    private val contactDao: ContactDao,
    private val mapper: ContactEntityMapper
) : LocalDataProvider {

    override suspend fun selectMyContacts(): Flow<List<Contact>> {
        return contactDao.selectMyContacts().map(mapper::fromEntityList)
    }

    override suspend fun selectScannedContacts(): Flow<List<Contact>> {
        return contactDao.selectScannedContacts().map(mapper::fromEntityList)
    }

    override suspend fun selectContactById(id: Int): Contact {
        return mapper.fromEntity(contactDao.selectContactById(id))
    }

    override suspend fun addContact(contact: Contact) {
        return contactDao.insert(mapper.toEntity(contact))
    }

    override suspend fun updateContact(contact: Contact) {
        return contactDao.update(mapper.toEntity(contact))
    }

    override suspend fun deleteContact(id: Int) {
        return contactDao.delete(id)
    }
}

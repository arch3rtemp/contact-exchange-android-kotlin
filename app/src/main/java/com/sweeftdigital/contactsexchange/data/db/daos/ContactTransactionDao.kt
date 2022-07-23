package com.sweeftdigital.contactsexchange.data.db.daos

import androidx.room.Dao
import androidx.room.Transaction
import com.sweeftdigital.contactsexchange.data.db.AppDatabase
import com.sweeftdigital.contactsexchange.data.db.models.ContactEntity

@Dao
abstract class ContactTransactionDao(private val database: AppDatabase) {
    @Transaction
    open suspend fun updateAll(arg: List<ContactEntity>): Boolean {
        database.contactDao().deleteAll()
        database.contactDao().insertAll(arg)
        return true
    }
}
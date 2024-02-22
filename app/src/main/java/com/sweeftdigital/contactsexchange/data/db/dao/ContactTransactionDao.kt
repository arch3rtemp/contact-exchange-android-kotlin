package com.sweeftdigital.contactsexchange.data.db.dao

import androidx.room.Dao
import androidx.room.Transaction
import com.sweeftdigital.contactsexchange.data.db.AppDatabase
import com.sweeftdigital.contactsexchange.data.db.model.ContactEntity

@Dao
abstract class ContactTransactionDao(private val database: AppDatabase) {
    @Transaction
    open suspend fun updateAll(arg: List<ContactEntity>): Boolean {
        database.contactDao().deleteAll()
        database.contactDao().insertAll(arg)
        return true
    }
}
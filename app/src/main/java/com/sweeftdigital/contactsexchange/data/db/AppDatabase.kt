package com.sweeftdigital.contactsexchange.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sweeftdigital.contactsexchange.data.db.converters.AllTypeConverters
import com.sweeftdigital.contactsexchange.data.db.daos.ContactDao
import com.sweeftdigital.contactsexchange.data.db.daos.ContactTransactionDao
import com.sweeftdigital.contactsexchange.data.db.models.ContactEntity

@Database(
    version = 1, exportSchema = false,
    entities = [ContactEntity::class]
)

@TypeConverters(AllTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun contactTransactionDao(): ContactTransactionDao
}
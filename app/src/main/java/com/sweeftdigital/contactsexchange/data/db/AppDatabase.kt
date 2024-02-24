package com.sweeftdigital.contactsexchange.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sweeftdigital.contactsexchange.data.db.converter.AllTypeConverters
import com.sweeftdigital.contactsexchange.data.db.dao.ContactDao
import com.sweeftdigital.contactsexchange.data.db.model.ContactEntity

@Database(version = 1, exportSchema = false, entities = [ContactEntity::class])
@TypeConverters(AllTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}
package dev.arch3rtemp.contactexchange.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.arch3rtemp.contactexchange.data.db.converter.AllTypeConverters
import dev.arch3rtemp.contactexchange.data.db.dao.ContactDao
import dev.arch3rtemp.contactexchange.data.db.model.ContactEntity

@Database(version = 1, exportSchema = false, entities = [ContactEntity::class])
@TypeConverters(AllTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}

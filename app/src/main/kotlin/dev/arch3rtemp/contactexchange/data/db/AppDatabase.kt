package dev.arch3rtemp.contactexchange.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.arch3rtemp.contactexchange.data.db.dao.ContactDao
import dev.arch3rtemp.contactexchange.data.db.model.ContactEntity

@Database(version = 1, exportSchema = false, entities = [ContactEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}

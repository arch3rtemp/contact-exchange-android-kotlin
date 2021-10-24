package com.sweeftdigital.contactsexchange.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sweeftdigital.contactsexchange.domain.models.Contact

@Database(
    version = 1, exportSchema = false,
    entities = [Contact::class]
)


abstract class Database : RoomDatabase() {
}
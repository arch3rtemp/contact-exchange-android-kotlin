package dev.arch3rtemp.contactexchange.data.db.converter

import androidx.room.TypeConverter
import java.util.Date

class AllTypeConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

}

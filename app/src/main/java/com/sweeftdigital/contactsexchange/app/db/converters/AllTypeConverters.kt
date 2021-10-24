package com.sweeftdigital.contactsexchange.app.db.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class AllTypeConverters {
    private val gson = GsonBuilder().create()

    @TypeConverter
    fun intsToJson(list: List<Int>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun jsonToInts(json: String): List<Int> {
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun stringsToJson(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun jsonToStrings(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }
}
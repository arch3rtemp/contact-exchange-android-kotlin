package com.sweeftdigital.contactsexchange.di.module

import androidx.room.Room
import com.sweeftdigital.contactsexchange.data.db.AppDatabase
import com.sweeftdigital.contactsexchange.data.providers.local.LocalDataProvider
import com.sweeftdigital.contactsexchange.data.providers.local.LocalDataProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val LOCAL_STORAGE_MODULE = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        LocalDataProviderImpl(get())
    } bind LocalDataProvider::class
}
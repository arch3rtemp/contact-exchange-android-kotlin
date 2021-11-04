package com.sweeftdigital.contactsexchange.app.modules

import androidx.room.Room
import com.sweeftdigital.contactsexchange.app.db.AppDatabase
import com.sweeftdigital.contactsexchange.domain.data_providers.local.LocalDataProvider
import com.sweeftdigital.contactsexchange.domain.data_providers.local.LocalDataProviderImpl
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
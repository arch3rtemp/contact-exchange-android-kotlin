package dev.arch3rtemp.contactexchange.di.module

import androidx.room.Room
import dev.arch3rtemp.contactexchange.data.db.AppDatabase
import dev.arch3rtemp.contactexchange.data.providers.local.LocalDataProvider
import dev.arch3rtemp.contactexchange.data.providers.local.LocalDataProviderImpl
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

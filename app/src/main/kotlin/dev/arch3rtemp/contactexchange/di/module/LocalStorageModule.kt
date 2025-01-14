package dev.arch3rtemp.contactexchange.di.module

import androidx.room.Room
import dev.arch3rtemp.contactexchange.data.db.AppDatabase
import dev.arch3rtemp.contactexchange.data.dataprovider.local.LocalDataProvider
import dev.arch3rtemp.contactexchange.data.dataprovider.local.LocalDataProviderImpl
import dev.arch3rtemp.contactexchange.data.db.dao.ContactDao
import dev.arch3rtemp.contactexchange.data.repository.ContactRepositoryImpl
import dev.arch3rtemp.contactexchange.domain.repository.ContactRepository
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
        get<AppDatabase>().contactDao()
    } bind ContactDao::class

    single {
        LocalDataProviderImpl(get(), get())
    } bind LocalDataProvider::class

    single {
        ContactRepositoryImpl(get())
    } bind ContactRepository::class
}

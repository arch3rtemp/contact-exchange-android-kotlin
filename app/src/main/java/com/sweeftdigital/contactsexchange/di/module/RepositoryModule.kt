package com.sweeftdigital.contactsexchange.di.module

import com.sweeftdigital.contactsexchange.domain.repository.Repository
import com.sweeftdigital.contactsexchange.data.repository.RepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val REPOSITORY_MODULE = module {
    single {
        RepositoryImpl(get())
    } bind Repository::class
}
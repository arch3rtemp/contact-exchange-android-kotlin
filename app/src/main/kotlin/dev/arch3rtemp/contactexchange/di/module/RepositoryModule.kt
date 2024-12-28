package dev.arch3rtemp.contactexchange.di.module

import dev.arch3rtemp.contactexchange.data.repository.RepositoryImpl
import dev.arch3rtemp.contactexchange.domain.repository.Repository
import org.koin.dsl.bind
import org.koin.dsl.module

val REPOSITORY_MODULE = module {
    single {
        RepositoryImpl(get())
    } bind Repository::class
}

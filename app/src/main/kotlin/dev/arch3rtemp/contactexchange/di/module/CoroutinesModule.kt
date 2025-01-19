package dev.arch3rtemp.contactexchange.di.module

import dev.arch3rtemp.contactexchange.domain.util.AppDispatcherProvider
import dev.arch3rtemp.contactexchange.domain.util.DispatcherProvider
import org.koin.dsl.bind
import org.koin.dsl.module

val COROUTINES_MODULE = module {
    factory {
        AppDispatcherProvider()
    } bind DispatcherProvider::class
}

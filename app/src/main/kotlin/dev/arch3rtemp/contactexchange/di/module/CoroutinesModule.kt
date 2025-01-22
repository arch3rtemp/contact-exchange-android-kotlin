package dev.arch3rtemp.contactexchange.di.module

import dev.arch3rtemp.ui.util.AppDispatcherProvider
import dev.arch3rtemp.tests.coroutines.DispatcherProvider
import org.koin.dsl.bind
import org.koin.dsl.module

val COROUTINES_MODULE = module {
    factory {
        AppDispatcherProvider()
    } bind DispatcherProvider::class
}

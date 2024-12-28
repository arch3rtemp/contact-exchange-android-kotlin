package dev.arch3rtemp.contactexchange.di.module

import dev.arch3rtemp.core_ui.util.StringResourceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val UTILS_MODULE = module {
    factory {
        StringResourceManager(androidContext())
    }
}

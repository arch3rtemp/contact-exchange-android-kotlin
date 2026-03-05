package dev.arch3rtemp.contactexchange.di.module

import dev.arch3rtemp.contactexchange.ui.util.ErrorMsgResolver
import dev.arch3rtemp.contactexchange.ui.util.StringResourceManager
import dev.arch3rtemp.contactexchange.ui.util.TimeConverter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val UTILS_MODULE = module {
    factory {
        StringResourceManager(androidContext())
    }

    factory {
        ErrorMsgResolver(get())
    }

    factory {
        TimeConverter()
    }
}

package com.sweeftdigital.contactsexchange.di.module

import com.sweeftdigital.contactsexchange.presentation.common.StringResourceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val UTILS_MODULE = module {
    factory {
        StringResourceManager(androidContext())
    }
}
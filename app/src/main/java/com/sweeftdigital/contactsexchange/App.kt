package com.sweeftdigital.contactsexchange

import android.app.Application
import com.sweeftdigital.contactsexchange.di.modules.LOCAL_STORAGE_MODULE
import com.sweeftdigital.contactsexchange.di.modules.REPOSITORY_MODULE
import com.sweeftdigital.contactsexchange.di.modules.USE_CASES_MODULE
import com.sweeftdigital.contactsexchange.di.modules.VIEW_MODELS_MODULE
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                LOCAL_STORAGE_MODULE,
                REPOSITORY_MODULE,
                USE_CASES_MODULE,
                VIEW_MODELS_MODULE
            )
        }
    }
}
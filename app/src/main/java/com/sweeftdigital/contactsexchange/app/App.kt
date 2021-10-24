package com.sweeftdigital.contactsexchange.app

import android.app.Application
import com.sweeftdigital.contactsexchange.app.modules.VIEW_MODELS_MODULE
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                VIEW_MODELS_MODULE
            )
        }
    }
}
package dev.arch3rtemp.contactexchange

import android.app.Application
import dev.arch3rtemp.contactexchange.di.module.COROUTINES_MODULE
import dev.arch3rtemp.contactexchange.di.module.LOCAL_STORAGE_MODULE
import dev.arch3rtemp.contactexchange.di.module.MAPPERS_MODULE
import dev.arch3rtemp.contactexchange.di.module.SCANNER_MODULE
import dev.arch3rtemp.contactexchange.di.module.USE_CASES_MODULE
import dev.arch3rtemp.contactexchange.di.module.UTILS_MODULE
import dev.arch3rtemp.contactexchange.di.module.VIEW_MODELS_MODULE
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                LOCAL_STORAGE_MODULE,
                USE_CASES_MODULE,
                VIEW_MODELS_MODULE,
                SCANNER_MODULE,
                MAPPERS_MODULE,
                COROUTINES_MODULE,
                UTILS_MODULE
            )
        }
    }
}

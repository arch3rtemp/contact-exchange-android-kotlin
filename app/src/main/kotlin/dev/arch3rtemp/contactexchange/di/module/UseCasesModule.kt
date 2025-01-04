package dev.arch3rtemp.contactexchange.di.module

import android.app.Activity
import dev.arch3rtemp.contactexchange.domain.usecase.DeleteContactUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.FilterContactsUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.GetContactByIdUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.GetMyCardsUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.GetScannedContactsUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.SaveContactUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.ScanQrUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.UpdateContactUseCase
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val USE_CASES_MODULE = module {
    factory {
        DeleteContactUseCase(get())
    }

    factory {
        SaveContactUseCase(get())
    }

    factory {
        GetContactByIdUseCase(get())
    }

    factory {
        GetMyCardsUseCase(get())
    }

    factory {
        GetScannedContactsUseCase(get())
    }

    factory {
        UpdateContactUseCase(get())
    }

    factory {
        FilterContactsUseCase()
    }

    factory { (activity: Activity) ->
        ScanQrUseCase(get { parametersOf(activity) })
    }
}

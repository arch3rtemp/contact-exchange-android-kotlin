package com.sweeftdigital.contactsexchange.di.modules

import com.sweeftdigital.contactsexchange.domain.useCases.*
import org.koin.dsl.module

val USE_CASES_MODULE = module {
    factory {
        DeleteContactUseCase(get())
    }

    factory {
        SaveContactUseCase(get())
    }

    factory {
        SelectContactByIdUseCase(get())
    }

    factory {
        SelectMyContactsUseCase(get())
    }

    factory {
        SelectScannedContactsUseCase(get())
    }

    factory {
        SelectAllContactsUseCase(get())
    }

    factory {
        UpdateContactUseCase(get())
    }
}
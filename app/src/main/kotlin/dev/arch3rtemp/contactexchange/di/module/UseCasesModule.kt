package dev.arch3rtemp.contactexchange.di.module

import dev.arch3rtemp.contactexchange.domain.use_case.DeleteContactUseCase
import dev.arch3rtemp.contactexchange.domain.use_case.SaveContactUseCase
import dev.arch3rtemp.contactexchange.domain.use_case.SelectContactByIdUseCase
import dev.arch3rtemp.contactexchange.domain.use_case.SelectMyContactsUseCase
import dev.arch3rtemp.contactexchange.domain.use_case.SelectScannedContactsUseCase
import dev.arch3rtemp.contactexchange.domain.use_case.UpdateContactUseCase
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
        UpdateContactUseCase(get())
    }
}

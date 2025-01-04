package dev.arch3rtemp.contactexchange.di.module

import dev.arch3rtemp.contactexchange.data.mapper.ContactEntityMapper
import dev.arch3rtemp.contactexchange.data.mapper.JsonToContactMapper
import dev.arch3rtemp.contactexchange.presentation.mapper.CardUiMapper
import dev.arch3rtemp.contactexchange.presentation.mapper.ContactUiMapper
import org.koin.dsl.module

val MAPPERS_MODULE = module {
    factory {
        ContactEntityMapper()
    }

    factory {
        JsonToContactMapper()
    }

    factory {
        ContactUiMapper(get())
    }

    factory {
        CardUiMapper()
    }
}

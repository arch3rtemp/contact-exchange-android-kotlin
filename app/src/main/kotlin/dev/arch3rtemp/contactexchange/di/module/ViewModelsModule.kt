package dev.arch3rtemp.contactexchange.di.module

import dev.arch3rtemp.contactexchange.presentation.MainViewModel
import dev.arch3rtemp.contactexchange.presentation.card.CardViewModel
import dev.arch3rtemp.contactexchange.presentation.create.CreateCardViewModel
import dev.arch3rtemp.contactexchange.presentation.edit.EditCardViewModel
import dev.arch3rtemp.contactexchange.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val VIEW_MODELS_MODULE = module {

    viewModel { HomeViewModel(get(), get(), get(), get()) }

    viewModel { CardViewModel(get(), get()) }

    viewModel { CreateCardViewModel(get(), get()) }

    viewModel { EditCardViewModel(get(), get(), get()) }

    viewModel { MainViewModel(get(), get()) }
}

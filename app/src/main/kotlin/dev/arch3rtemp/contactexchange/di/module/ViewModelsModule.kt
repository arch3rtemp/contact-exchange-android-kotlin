package dev.arch3rtemp.contactexchange.di.module

import dev.arch3rtemp.contactexchange.presentation.ui.MainViewModel
import dev.arch3rtemp.contactexchange.presentation.ui.card.CardViewModel
import dev.arch3rtemp.contactexchange.presentation.ui.create.CreateCardViewModel
import dev.arch3rtemp.contactexchange.presentation.ui.edit.EditCardViewModel
import dev.arch3rtemp.contactexchange.presentation.ui.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val VIEW_MODELS_MODULE = module {

    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }

    viewModel { CardViewModel(get(), get(), get(), get()) }

    viewModel { CreateCardViewModel(get(), get(), get()) }

    viewModel { EditCardViewModel(get(), get(), get(), get(), get()) }

    viewModel { MainViewModel(get(), get(), get()) }
}

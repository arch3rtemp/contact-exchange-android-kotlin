package com.sweeftdigital.contactsexchange.di.module

import com.sweeftdigital.contactsexchange.presentation.MainViewModel
import com.sweeftdigital.contactsexchange.presentation.card.CardViewModel
import com.sweeftdigital.contactsexchange.presentation.create.CreateCardViewModel
import com.sweeftdigital.contactsexchange.presentation.edit.EditCardViewModel
import com.sweeftdigital.contactsexchange.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val VIEW_MODELS_MODULE = module {

    viewModel { HomeViewModel(get(), get(), get(), get()) }

    viewModel { CardViewModel(get(), get()) }

    viewModel { CreateCardViewModel(get(), get()) }

    viewModel { EditCardViewModel(get(), get()) }

    viewModel { MainViewModel(get(), get()) }
}
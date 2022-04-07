package com.sweeftdigital.contactsexchange.app.modules

import com.sweeftdigital.contactsexchange.presentation.main.card.CardViewModel
import com.sweeftdigital.contactsexchange.presentation.main.create.CreateCardViewModel
import com.sweeftdigital.contactsexchange.presentation.main.edit.EditCardViewModel
import com.sweeftdigital.contactsexchange.presentation.main.home.HomeViewModel
import com.sweeftdigital.contactsexchange.presentation.qr.QrViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val VIEW_MODELS_MODULE = module {

    viewModel { HomeViewModel(get(), get(), get(), get()) }

    viewModel { CardViewModel(get(), get()) }

    viewModel { CreateCardViewModel(get()) }

    viewModel { EditCardViewModel(get(), get()) }

    viewModel { QrViewModel(get()) }
}
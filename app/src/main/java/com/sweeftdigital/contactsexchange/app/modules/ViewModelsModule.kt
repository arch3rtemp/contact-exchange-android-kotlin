package com.sweeftdigital.contactsexchange.app.modules

import com.sweeftdigital.contactsexchange.presentation.home.HomeViewModel
import com.sweeftdigital.contactsexchange.presentation.qr.QrViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val VIEW_MODELS_MODULE = module {
    viewModel {
        HomeViewModel()
    }

    viewModel {
        QrViewModel(get())
    }
}
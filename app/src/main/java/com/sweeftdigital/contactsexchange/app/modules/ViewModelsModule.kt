package com.sweeftdigital.contactsexchange.app.modules

import com.sweeftdigital.contactsexchange.presentation.main.MainViewModel
import com.sweeftdigital.contactsexchange.presentation.main.create.CreateCardViewModel
import com.sweeftdigital.contactsexchange.presentation.main.home.HomeViewModel
import com.sweeftdigital.contactsexchange.presentation.qr.QrViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val VIEW_MODELS_MODULE = module {
    viewModel {
        MainViewModel()
    }

    viewModel {
        HomeViewModel(get(), get(), get(), get())
    }

    viewModel {
        CreateCardViewModel(get())
    }

    viewModel {
        QrViewModel(get())
    }
}
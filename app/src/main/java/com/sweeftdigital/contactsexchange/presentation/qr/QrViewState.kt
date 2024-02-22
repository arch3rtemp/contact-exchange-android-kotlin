package com.sweeftdigital.contactsexchange.presentation.qr

import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEffect
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEvent
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiState

sealed interface ViewState {
    data object Idle: ViewState
    data object Loading: ViewState
    data object Error : ViewState
    data class Success(val data: Contact = Contact()) : ViewState
}

sealed interface QrEvent : UiEvent {
    data class OnQrScan(val contact: Contact) : QrEvent
}

sealed interface QrEffect : UiEffect {
    data class Error(val message: String) : QrEffect
}

data class QrState(val viewState: ViewState) : UiState
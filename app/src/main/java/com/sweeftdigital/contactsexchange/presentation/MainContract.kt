package com.sweeftdigital.contactsexchange.presentation

import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEffect
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEvent
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiState

sealed interface MainEvent : UiEvent {
    data class OnQrScanComplete(val contact: Contact) : MainEvent
    data object OnQrScanCanceled : MainEvent
    data class OnQrScanFail(val message: String?) : MainEvent
}

sealed interface MainEffect : UiEffect {
    data class ShowMessage(val message: String) : MainEffect
}

data object MainState : UiState
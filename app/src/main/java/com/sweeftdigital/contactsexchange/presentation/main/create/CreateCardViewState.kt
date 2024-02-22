package com.sweeftdigital.contactsexchange.presentation.main.create

import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEffect
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEvent
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiState

sealed interface ViewState {
    data object Idle: ViewState
    data object Error : ViewState
    data object Success : ViewState
}

sealed interface CreateCardEvent : UiEvent {
    data class OnCreateButtonPressed(val contact: Contact) : CreateCardEvent
}

sealed interface CreateCardEffect : UiEffect {
    data class Error(val message: String) : CreateCardEffect
    data object Success : CreateCardEffect
}

data class CreateCardState(val viewState: ViewState) : UiState
package com.sweeftdigital.contactsexchange.presentation.main.edit

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

sealed interface EditCardEvent : UiEvent {
    data class OnCardLoaded(val id: Int) : EditCardEvent
    data class OnSaveButtonPressed(val contact: Contact) : EditCardEvent
}

sealed interface EditCardEffect : UiEffect {
    data class Error(val message: String) : EditCardEffect
    data object Finish : EditCardEffect
}

data class EditCardState(val viewState: ViewState) : UiState
package com.sweeftdigital.contactsexchange.presentation.main.home

import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEffect
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEvent
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiState

sealed interface ViewState {
    data object Empty : ViewState
    data object Loading : ViewState
    data object Error : ViewState
    data class Success(val data: List<Contact>) : ViewState
}

sealed interface HomeEvent : UiEvent {
    data object OnContactsLoad : HomeEvent
    data class OnContactDeleted(val contact: Contact) : HomeEvent
    data class OnContactSaved(val contact: Contact) : HomeEvent
    data class OnSearchTyped(val query: String) : HomeEvent
}

sealed interface HomeEffect : UiEffect {
    data class Error(val message: String) : HomeEffect
    data class Deleted(val contact: Contact) : HomeEffect
}

data class HomeState(val cardsState: ViewState, val contactsState: ViewState, val query: String = "") : UiState
package dev.arch3rtemp.contactexchange.presentation.home

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.core_ui.base.marker.UiEffect
import dev.arch3rtemp.core_ui.base.marker.UiEvent
import dev.arch3rtemp.core_ui.base.marker.UiState

sealed interface ViewState {
    data object Empty : ViewState
    data object Loading : ViewState
    data object Error : ViewState
    data class Success(val data: List<Contact>) : ViewState
}

sealed interface HomeEvent : UiEvent {
    data object OnContactsLoad : HomeEvent
    data class OnContactDelete(val contact: Contact) : HomeEvent
    data class OnContactSaved(val contact: Contact) : HomeEvent
    data class OnSearchTyped(val query: String) : HomeEvent
}

sealed interface HomeEffect : UiEffect {
    data class Error(val message: String) : HomeEffect
    data class Deleted(val contact: Contact) : HomeEffect
}

data class HomeState(val cardsState: ViewState, val contactsState: ViewState, val query: String = "") :
    UiState

package dev.arch3rtemp.contactexchange.presentation.ui.home

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.presentation.model.CardUi
import dev.arch3rtemp.contactexchange.presentation.model.ContactUi
import dev.arch3rtemp.ui.base.marker.UiEffect
import dev.arch3rtemp.ui.base.marker.UiEvent
import dev.arch3rtemp.ui.base.marker.UiState

sealed interface HomeEvent : UiEvent {
    data object OnContactsLoad : HomeEvent
    data object OnCardsLoad : HomeEvent
    data class OnContactDelete(val contact: ContactUi) : HomeEvent
    data class OnContactSave(val contact: Contact) : HomeEvent
    @JvmInline
    value class OnSearchTyped(val query: String) : HomeEvent
}

sealed interface HomeEffect : UiEffect {
    @JvmInline
    value class ShowError(val message: String) : HomeEffect
    data class ShowUndo(val contact: Contact) : HomeEffect
}

sealed interface ContactState {
    data object Idle : ContactState
    data object Empty : ContactState
    data object Loading : ContactState
    @JvmInline
    value class Error(val message: String) : ContactState
    data class Success(val data: List<ContactUi>) : ContactState
}

sealed interface CardState {
    data object Idle : CardState
    data object Empty : CardState
    data object Loading : CardState
    @JvmInline
    value class Error(val message: String) : CardState
    data class Success(val data: List<CardUi>) : CardState
}

data class HomeState(val cardsState: CardState, val contactsState: ContactState) : UiState

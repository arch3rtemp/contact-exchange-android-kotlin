package com.sweeftdigital.contactsexchange.presentation.card

import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEffect
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEvent
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiState

sealed interface ViewState {
    data object Empty : ViewState
    data object Loading : ViewState
    data object Error : ViewState
    data class Success(val data: Contact) : ViewState
}

sealed interface CardEvent : UiEvent {
    data class OnCardLoaded(val id: Int) : CardEvent
    data class OnCardDeleted(val id: Int) : CardEvent
}

sealed interface CardEffect : UiEffect {
    data class Error(val message: String) : CardEffect
}

data class CardState(val viewState: ViewState) : UiState
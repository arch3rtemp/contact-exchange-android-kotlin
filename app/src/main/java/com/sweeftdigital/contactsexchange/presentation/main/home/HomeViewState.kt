package com.sweeftdigital.contactsexchange.presentation.main.home

import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.presentation.base.markers.EffectMarker
import com.sweeftdigital.contactsexchange.presentation.base.markers.EventMarker
import com.sweeftdigital.contactsexchange.presentation.base.markers.StateMarker

sealed class ViewState {
    object Empty : ViewState()
    object Loading : ViewState()
    object Error : ViewState()
    object Success : ViewState()
}

sealed class HomeEvent : EventMarker {
    object OnContactsLoaded : HomeEvent()
    data class OnContactDeleted(val contact: Contact) : HomeEvent()
    data class OnContactSaved(val contact: Contact) : HomeEvent()
    data class OnSearchTyped(val searched: String) : HomeEvent()
}

sealed class HomeEffect : EffectMarker {
    data class Error(val message: String) : HomeEffect()
    data class Deleted(val contact: Contact) : HomeEffect()
    data class Searched(val searched: String) : HomeEffect()
}

data class HomeState(val cardsState: CardsState, val contactsState: ContactsState) : StateMarker

data class CardsState(
    val viewState: ViewState,
    val myCards: List<Contact> = listOf()
)
data class ContactsState(
    val viewState: ViewState,
    val contacts: List<Contact> = listOf(),
    val searched: String = ""
)
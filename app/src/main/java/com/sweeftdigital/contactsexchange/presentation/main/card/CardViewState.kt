package com.sweeftdigital.contactsexchange.presentation.main.card

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

sealed class CardEvent : EventMarker {
    data class OnCardLoaded(val id: Int) : CardEvent()
    data class OnCardDeleted(val id: Int) : CardEvent()
}

sealed class CardEffect : EffectMarker {
    data class Error(val message: String) : CardEffect()
}

data class CardState(
    val viewState: ViewState,
    val contact: Contact = Contact()
) : StateMarker
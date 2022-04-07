package com.sweeftdigital.contactsexchange.presentation.main.create

import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.presentation.base.markers.EffectMarker
import com.sweeftdigital.contactsexchange.presentation.base.markers.EventMarker
import com.sweeftdigital.contactsexchange.presentation.base.markers.StateMarker

sealed class ViewState {
    object Idle: ViewState()
    object Error : ViewState()
    object Success : ViewState()
}

sealed class CreateCardEvent : EventMarker {
    data class OnCreateButtonPressed(val contact: Contact) : CreateCardEvent()
}

sealed class CreateCardEffect : EffectMarker {
    data class Error(val message: String) : CreateCardEffect()
    object Success : CreateCardEffect()
}

data class CreateCardState(
    val viewState: ViewState
) : StateMarker
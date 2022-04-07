package com.sweeftdigital.contactsexchange.presentation.main.edit

import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.presentation.base.markers.EffectMarker
import com.sweeftdigital.contactsexchange.presentation.base.markers.EventMarker
import com.sweeftdigital.contactsexchange.presentation.base.markers.StateMarker

sealed class ViewState {
    object Idle: ViewState()
    object Loading: ViewState()
    object Error : ViewState()
    object Success : ViewState()
}

sealed class EditCardEvent : EventMarker {
    data class OnCardLoaded(val id: Int) : EditCardEvent()
    data class OnSaveButtonPressed(val contact: Contact) : EditCardEvent()
}

sealed class EditCardEffect : EffectMarker {
    data class Error(val message: String) : EditCardEffect()
    object Finish : EditCardEffect()
}

data class EditCardState(
    val viewState: ViewState,
    val contact: Contact = Contact()
) : StateMarker
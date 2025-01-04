package dev.arch3rtemp.contactexchange.presentation.ui.create

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.ui.base.marker.UiEffect
import dev.arch3rtemp.ui.base.marker.UiEvent
import dev.arch3rtemp.ui.base.marker.UiState

sealed interface CreateCardEvent : UiEvent {
    data class OnCreateButtonPress(val contact: Contact) : CreateCardEvent
}

sealed interface CreateCardEffect : UiEffect {
    @JvmInline
    value class ShowError(val message: String) : CreateCardEffect
    data object NavigateUp : CreateCardEffect
}

data object CreateCardState : UiState

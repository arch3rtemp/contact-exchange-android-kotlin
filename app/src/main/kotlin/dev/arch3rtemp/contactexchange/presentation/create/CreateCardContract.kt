package dev.arch3rtemp.contactexchange.presentation.create

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.core_ui.base.marker.UiEffect
import dev.arch3rtemp.core_ui.base.marker.UiEvent
import dev.arch3rtemp.core_ui.base.marker.UiState

sealed interface ViewState {
    data object Idle: ViewState
    data object Error : ViewState
    data object Success : ViewState
}

sealed interface CreateCardEvent : UiEvent {
    data class OnCreateButtonPressed(val contact: Contact) : CreateCardEvent
}

sealed interface CreateCardEffect : UiEffect {
    data class Error(val message: String) : CreateCardEffect
    data object Success : CreateCardEffect
}

data class CreateCardState(val viewState: ViewState) : UiState

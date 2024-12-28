package dev.arch3rtemp.contactexchange.presentation.card

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.core_ui.base.marker.UiEffect
import dev.arch3rtemp.core_ui.base.marker.UiEvent
import dev.arch3rtemp.core_ui.base.marker.UiState

sealed interface ViewState {
    data object Idle : ViewState
    data object Loading : ViewState
    data object Error : ViewState
    data class Success(val data: Contact) : ViewState
}

sealed interface CardEvent : UiEvent {
    data class OnCardLoad(val id: Int) : CardEvent
    data class OnCardDelete(val id: Int) : CardEvent
}

sealed interface CardEffect : UiEffect {
    data class ShowError(val message: String) : CardEffect
    data object AnimateDeletion : CardEffect
}

data class CardState(val viewState: ViewState) : UiState

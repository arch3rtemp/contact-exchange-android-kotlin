package dev.arch3rtemp.contactexchange.presentation.ui.card

import dev.arch3rtemp.contactexchange.presentation.model.ContactUi
import dev.arch3rtemp.ui.base.marker.UiEffect
import dev.arch3rtemp.ui.base.marker.UiEvent
import dev.arch3rtemp.ui.base.marker.UiState

sealed interface ViewState {
    data object Idle : ViewState
    data object Loading : ViewState
    data object Error : ViewState
    data class Success(val data: ContactUi) : ViewState
}

sealed interface CardEvent : UiEvent {
    @JvmInline
    value class OnCardLoad(val id: Int) : CardEvent
    @JvmInline
    value class OnCardDelete(val id: Int) : CardEvent
}

sealed interface CardEffect : UiEffect {
    @JvmInline
    value class ShowError(val message: String) : CardEffect
    data object AnimateDeletion : CardEffect
}

data class CardState(val viewState: ViewState) : UiState

package dev.arch3rtemp.contactexchange.presentation.ui.edit

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.presentation.model.ContactUi
import dev.arch3rtemp.ui.base.marker.UiEffect
import dev.arch3rtemp.ui.base.marker.UiEvent
import dev.arch3rtemp.ui.base.marker.UiState

sealed interface ViewState {
    data object Idle: ViewState
    data object Loading: ViewState
    data object Error : ViewState
    data class Success(val data: ContactUi) : ViewState
}

sealed interface EditCardEvent : UiEvent {
    @JvmInline
    value class OnCardLoad(val id: Int) : EditCardEvent
    data class OnUpdateButtonPress(val contact: Contact) : EditCardEvent
}

sealed interface EditCardEffect : UiEffect {
    @JvmInline
    value class ShowError(val message: String) : EditCardEffect
    data object NavigateUp : EditCardEffect
}

data class EditCardState(val viewState: ViewState) : UiState

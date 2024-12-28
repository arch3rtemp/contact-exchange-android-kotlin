package dev.arch3rtemp.contactexchange.presentation

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.core_ui.base.marker.UiEffect
import dev.arch3rtemp.core_ui.base.marker.UiEvent
import dev.arch3rtemp.core_ui.base.marker.UiState

sealed interface MainEvent : UiEvent {
    data class OnQrScanComplete(val contact: Contact) : MainEvent
    data object OnQrScanCanceled : MainEvent
    data class OnQrScanFail(val message: String?) : MainEvent
}

sealed interface MainEffect : UiEffect {
    data class ShowMessage(val message: String) : MainEffect
}

data object MainState : UiState

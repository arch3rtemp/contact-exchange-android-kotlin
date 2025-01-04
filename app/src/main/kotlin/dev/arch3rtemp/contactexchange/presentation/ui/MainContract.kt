package dev.arch3rtemp.contactexchange.presentation.ui

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.ui.base.marker.UiEffect
import dev.arch3rtemp.ui.base.marker.UiEvent
import dev.arch3rtemp.ui.base.marker.UiState

sealed interface MainEvent : UiEvent {
    data class OnQrScanComplete(val contact: Contact) : MainEvent
    @JvmInline
    value class OnQrScanCanceled(val message: String?) : MainEvent
    @JvmInline
    value class OnQrScanFail(val message: String?) : MainEvent
}

sealed interface MainEffect : UiEffect {
    @JvmInline
    value class ShowMessage(val message: String) : MainEffect
}

data object MainState : UiState

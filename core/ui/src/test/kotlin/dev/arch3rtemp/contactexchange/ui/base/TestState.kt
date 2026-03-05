package dev.arch3rtemp.contactexchange.ui.base

import dev.arch3rtemp.contactexchange.ui.base.marker.UiState

@JvmInline
value class TestState(val state: String) : UiState {
    fun copy(state: String): TestState {
        return TestState(state)
    }
}

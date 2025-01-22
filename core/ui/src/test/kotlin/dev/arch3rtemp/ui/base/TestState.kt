package dev.arch3rtemp.ui.base

import dev.arch3rtemp.ui.base.marker.UiState

@JvmInline
value class TestState(val state: String) : UiState {
    fun copy(state: String): TestState {
        return TestState(state)
    }
}

package dev.arch3rtemp.ui.base

import dev.arch3rtemp.ui.base.marker.UiEvent

@JvmInline
value class TestEvent(val event: String) : UiEvent

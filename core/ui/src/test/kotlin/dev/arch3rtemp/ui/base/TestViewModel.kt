package dev.arch3rtemp.ui.base

class TestViewModel : BaseViewModel<TestEvent, TestEffect, TestState>() {

    override fun createInitialState() = TestState(INITIAL_STATE)

    override fun handleEvent(event: TestEvent) {
        setState { copy("$HANDLED_EVENT${event.event}") }
    }

    fun triggerEffect(effect: TestEffect) {
        setEffect { effect }
    }

    companion object {
        const val INITIAL_STATE = "Initial State"
        const val HANDLED_EVENT = "Handled Event: "
    }
}

package com.sweeftdigital.contactsexchange.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEffect
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiEvent
import com.sweeftdigital.contactsexchange.presentation.base.marker.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UiEvent, Effect : UiEffect, State : UiState> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    private val _event: Channel<Event> = Channel()
    val event = _event.receiveAsFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _state: MutableLiveData<State> = MutableLiveData(initialState)
    open val state: LiveData<State> = _state

    val currentState: State? get() = state.value

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(event: Event)

    fun setEvent(event: Event) {
        val newEvent = event
        viewModelScope.launch { _event.send(newEvent) }
    }

    protected fun setState(reduce: State.() -> State) {
        _state.value = currentState?.reduce()
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }
}
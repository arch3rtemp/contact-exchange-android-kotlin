package com.sweeftdigital.contactsexchange.presentation.main.card

import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.useCases.DeleteContactUseCase
import com.sweeftdigital.contactsexchange.domain.useCases.SelectContactByIdUseCase
import com.sweeftdigital.contactsexchange.presentation.base.BaseViewModel
import com.sweeftdigital.contactsexchange.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CardViewModel(
    private val deleteContactUseCase: DeleteContactUseCase,
    private val selectContactByIdUseCase: SelectContactByIdUseCase
) : BaseViewModel<CardEvent, CardEffect, CardState>() {
    override fun createInitialState(): CardState {
        return CardState(
            viewState = ViewState.Empty
        )
    }

    override fun handleEvent(event: CardEvent) {
        when(event) {
            is CardEvent.OnCardLoaded -> { getCard(event.id) }
            is CardEvent.OnCardDeleted -> { deleteCard(event.id) }
        }
    }

    private fun getCard(id: Int) {
        viewModelScope.launch {
            selectContactByIdUseCase.start(id)
                .onStart { setState { copy(viewState = ViewState.Loading) } }
                .catch { setStateError(it.message.toString()) }
                .collect {
                    setState { copy(viewState = ViewState.Success, contact = it) } }
        }
    }

    private fun deleteCard(id: Int) {
        viewModelScope.launch {
            deleteContactUseCase.start(id)
                .onStart { setState { copy(viewState = ViewState.Loading) } }
                .catch { setStateError(it.message.toString()) }
                .collect {
//                    setState { copy(viewState = ViewState.Empty, contact = Contact.Empty) }
                }
        }
    }

    private fun setStateError(message: String) {
        setState { copy(viewState = ViewState.Error) }
        setEffect { CardEffect.Error(message) }
    }
}
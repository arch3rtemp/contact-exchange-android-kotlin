package dev.arch3rtemp.contactexchange.presentation.card

import androidx.lifecycle.viewModelScope
import dev.arch3rtemp.contactexchange.domain.use_case.DeleteContactUseCase
import dev.arch3rtemp.contactexchange.domain.use_case.SelectContactByIdUseCase
import dev.arch3rtemp.core_ui.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CardViewModel(
    private val deleteContactUseCase: DeleteContactUseCase,
    private val selectContactByIdUseCase: SelectContactByIdUseCase
) : BaseViewModel<CardEvent, CardEffect, CardState>() {

    override fun createInitialState(): CardState {
        return CardState(viewState = ViewState.Idle)
    }

    override fun handleEvent(event: CardEvent) {
        when(event) {
            is CardEvent.OnCardLoad -> { getCard(event.id) }
            is CardEvent.OnCardDelete -> { deleteCard(event.id) }
        }
    }

    private fun getCard(id: Int) {
        viewModelScope.launch {
            selectContactByIdUseCase.start(id)
                .onStart { setState { copy(viewState = ViewState.Loading) } }
                .catch { setStateError(it.message.toString()) }
                .collect {
                    setState { copy(viewState = ViewState.Success(it)) }
                }
        }
    }

    private fun deleteCard(id: Int) {
        viewModelScope.launch {
            deleteContactUseCase.start(id)
                .onStart { setState { copy(viewState = ViewState.Loading) } }
                .catch { setStateError(it.message.toString()) }
                .collect {
                    setEffect { CardEffect.AnimateDeletion }
                }
        }
    }

    private fun setStateError(message: String) {
        setState { copy(viewState = ViewState.Error) }
        setEffect { CardEffect.ShowError(message) }
    }
}

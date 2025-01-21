package dev.arch3rtemp.contactexchange.presentation.ui.card

import androidx.lifecycle.viewModelScope
import dev.arch3rtemp.contactexchange.domain.usecase.DeleteContactUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.GetContactByIdUseCase
import dev.arch3rtemp.contactexchange.presentation.mapper.ContactUiMapper
import dev.arch3rtemp.ui.base.BaseViewModel
import dev.arch3rtemp.ui.util.ErrorMsgResolver
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class CardViewModel(
    private val getContactById: GetContactByIdUseCase,
    private val deleteContact: DeleteContactUseCase,
    private val mapper: ContactUiMapper,
    private val errorMsgResolver: ErrorMsgResolver
) : BaseViewModel<CardEvent, CardEffect, CardState>() {

    private fun createErrorHandler(onError: () -> Unit = {}): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { context, throwable ->
            setEffect { CardEffect.ShowError(errorMsgResolver.resolve(throwable.localizedMessage)) }
            onError()
        }
    }

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
        viewModelScope.launch(createErrorHandler { setState { copy(ViewState.Error) } }) {
            setState { copy(viewState = ViewState.Loading) }
            val card = getContactById.invoke(id)
            setState { copy(viewState = ViewState.Success(mapper.toUiModel(card))) }
        }
    }

    private fun deleteCard(id: Int) {
        viewModelScope.launch(createErrorHandler()) {
            deleteContact.invoke(id)
            setEffect { CardEffect.AnimateDeletion }
        }
    }
}

package dev.arch3rtemp.contactexchange.presentation.ui.edit

import androidx.lifecycle.viewModelScope
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.usecase.GetContactByIdUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.UpdateContactUseCase
import dev.arch3rtemp.contactexchange.presentation.mapper.ContactUiMapper
import dev.arch3rtemp.ui.base.BaseViewModel
import dev.arch3rtemp.ui.util.ErrorMsgResolver
import dev.arch3rtemp.ui.util.StringResourceManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class EditCardViewModel(
    private val getContactById: GetContactByIdUseCase,
    private val updateContact: UpdateContactUseCase,
    private val mapper: ContactUiMapper,
    private val resourceManager: StringResourceManager,
    private val errorMsgResolver: ErrorMsgResolver
) : BaseViewModel<EditCardEvent, EditCardEffect, EditCardState>() {

    private fun createErrorHandler(onError: () -> Unit = {}): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { context, throwable ->
            setEffect { EditCardEffect.ShowError(errorMsgResolver.resolve(throwable.localizedMessage)) }
            onError()
        }
    }

    override fun createInitialState(): EditCardState {
        return EditCardState(ViewState.Idle)
    }

    override fun handleEvent(event: EditCardEvent) {
        when(event) {
            is EditCardEvent.OnCardLoad -> getCard(event.id)
            is EditCardEvent.OnUpdateButtonPress -> {
                updateCard(event.contact)
            }
        }
    }

    private fun getCard(id: Int) {
        viewModelScope.launch(createErrorHandler { setState { copy(ViewState.Error) } }) {
            setState { copy(viewState = ViewState.Loading) }
            val card = getContactById(id)
            setState { copy(viewState = ViewState.Success(mapper.toUiModel(card))) }
        }
    }

    private fun updateCard(newCard: Contact) {

        if (newCard.isNotBlank()) {
            (currentState?.viewState as? ViewState.Success)?.let { state ->
                state.data.let { currentCard ->

                    viewModelScope.launch(createErrorHandler()) {
                        updateContact(mapper.fromUiModel(currentCard), newCard)
                        setEffect { EditCardEffect.NavigateUp }
                    }
                }
            }
        } else {
            setEffect { EditCardEffect.ShowError(resourceManager.string(R.string.msg_all_fields_required)) }
        }
    }
}

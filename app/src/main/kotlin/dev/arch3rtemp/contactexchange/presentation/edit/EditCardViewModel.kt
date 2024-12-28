package dev.arch3rtemp.contactexchange.presentation.edit

import androidx.lifecycle.viewModelScope
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.use_case.SelectContactByIdUseCase
import dev.arch3rtemp.contactexchange.domain.use_case.UpdateContactUseCase
import dev.arch3rtemp.core_ui.base.BaseViewModel
import dev.arch3rtemp.core_ui.util.StringResourceManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class EditCardViewModel(
    private val selectContactByIdUseCase: SelectContactByIdUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val resourceManager: StringResourceManager
) : BaseViewModel<EditCardEvent, EditCardEffect, EditCardState>() {
    override fun createInitialState(): EditCardState {
        return EditCardState(
            viewState = ViewState.Idle
        )
    }

    override fun handleEvent(event: EditCardEvent) {
        when(event) {
            is EditCardEvent.OnCardLoad -> getCard(event.id)
            is EditCardEvent.OnSaveButtonPressed -> {
                updateCard(event.contact)
            }
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

    private fun updateCard(newCard: Contact) {
        if (checkContactNotBlank(newCard)) {
            (currentState?.viewState as? ViewState.Success)?.let { state ->
                state.data.let { currentCard ->
                    val mergedContact = mergeCard(currentCard, newCard)
                    viewModelScope.launch {

                        updateContactUseCase.start(mergedContact)
                            .onStart { setState { copy(viewState = ViewState.Loading) } }
                            .catch { setStateError(it.message.toString()) }
                            .collect {
                                setState { copy(viewState = ViewState.Success(newCard)) }
                                setEffect { EditCardEffect.Finish }
                            }

                    }
                }
            }
        } else {
            setStateError(resourceManager.string(R.string.msg_all_fields_required))
        }
    }

    private fun mergeCard(current: Contact, newCard: Contact): Contact {
        return Contact(
            current.id,
            newCard.name,
            newCard.job,
            newCard.position,
            newCard.email,
            newCard.phoneMobile,
            newCard.phoneOffice,
            newCard.createDate,
            current.color,
            current.isMy
        )
    }

    private fun checkContactNotBlank(contact: Contact): Boolean {
        contact.apply {
            return (name.isNotBlank()
                    && job.isNotBlank()
                    && position.isNotBlank()
                    && email.isNotBlank()
                    && phoneMobile.isNotBlank()
                    && phoneOffice.isNotBlank())
        }
    }

    private fun setStateError(message: String) {
        setState { copy(viewState = ViewState.Error) }
        setEffect { EditCardEffect.Error(message) }
    }
}

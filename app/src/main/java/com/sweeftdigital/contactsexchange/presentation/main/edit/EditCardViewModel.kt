package com.sweeftdigital.contactsexchange.presentation.main.edit

import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.useCases.SelectContactByIdUseCase
import com.sweeftdigital.contactsexchange.domain.useCases.UpdateContactUseCase
import com.sweeftdigital.contactsexchange.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class EditCardViewModel(
    private val selectContactByIdUseCase: SelectContactByIdUseCase,
    private val updateContactUseCase: UpdateContactUseCase
) : BaseViewModel<EditCardEvent, EditCardEffect, EditCardState>() {
    override fun createInitialState(): EditCardState {
        return EditCardState(
            viewState = ViewState.Idle
        )
    }

    override fun handleEvent(event: EditCardEvent) {
        when(event) {
            is EditCardEvent.OnCardLoaded -> getCard(event.id)
            is EditCardEvent.OnSaveButtonPressed -> {
                setState { copy(contact = event.contact) }
                saveCard(currentState!!.contact)
            }
        }
    }

    private fun getCard(id: Int) {
        viewModelScope.launch {
            selectContactByIdUseCase.start(id)
                .onStart { setState { copy(viewState = ViewState.Loading) } }
                .catch { setStateError(it.message.toString()) }
                .collect {
                    setState { copy(viewState = ViewState.Success, contact = it) }
                }
        }
    }

    private fun saveCard(contact: Contact) {
        viewModelScope.launch {
            if (checkContactNotBlank(contact)) {
                updateContactUseCase.start(contact)
                    .onStart { setState { copy(viewState = ViewState.Loading) } }
                    .catch { setStateError(it.message.toString()) }
                    .collect {
                        setState { copy(viewState = ViewState.Success) }
                        setEffect { EditCardEffect.Finish }
                    }
            } else {
                setStateError("Fill all fields!")
            }
        }
    }

    private fun checkContactNotBlank(contact: Contact): Boolean {
        contact.apply {
            if (
                name.isNotBlank()
                && job.isNotBlank()
                && position.isNotBlank()
                && email.isNotBlank()
                && phoneMobile.isNotBlank()
                && phoneOffice.isNotBlank()
            ) {
                return true
            }
            return false
        }
    }

    private fun setStateError(message: String) {
        setState {
            copy(
                viewState = ViewState.Error
            )
        }
        setEffect { EditCardEffect.Error(message) }
    }
}
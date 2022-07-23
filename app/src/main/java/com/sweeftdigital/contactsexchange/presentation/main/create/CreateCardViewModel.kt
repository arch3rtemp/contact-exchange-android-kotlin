package com.sweeftdigital.contactsexchange.presentation.main.create

import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.useCases.SaveContactUseCase
import com.sweeftdigital.contactsexchange.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CreateCardViewModel(
    private val saveContactUseCase: SaveContactUseCase
) : BaseViewModel<CreateCardEvent, CreateCardEffect, CreateCardState>() {

    override fun createInitialState(): CreateCardState {
        return CreateCardState(
            viewState = ViewState.Idle
        )
    }

    private fun saveCard(contact: Contact) {
        viewModelScope.launch {
            if (checkContactNotBlank(contact)) {
                saveContactUseCase.start(contact)
                    .catch { setStateError(it.message.toString()) }
                    .collect {
                        setStateSuccess()
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
        setEffect { CreateCardEffect.Error(message) }
    }

    private fun setStateSuccess() {
        setState { copy(viewState = ViewState.Success) }
        setEffect { CreateCardEffect.Success }
    }

    override fun handleEvent(event: CreateCardEvent) {
        when(event) {
            is CreateCardEvent.OnCreateButtonPressed -> { saveCard(event.contact) }
        }
    }
}
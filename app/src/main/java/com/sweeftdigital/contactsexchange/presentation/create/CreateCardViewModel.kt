package com.sweeftdigital.contactsexchange.presentation.create

import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.R
import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.domain.use_case.SaveContactUseCase
import dev.arch3rtemp.core_ui.base.BaseViewModel
import dev.arch3rtemp.core_ui.util.StringResourceManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CreateCardViewModel(
    private val saveContactUseCase: SaveContactUseCase,
    private val resourceManager: StringResourceManager
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
                setStateError(resourceManager.string(R.string.msg_all_fields_required))
            }
        }
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
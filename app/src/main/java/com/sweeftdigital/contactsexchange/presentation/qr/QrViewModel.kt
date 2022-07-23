package com.sweeftdigital.contactsexchange.presentation.qr

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.useCases.SaveContactUseCase
import com.sweeftdigital.contactsexchange.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class QrViewModel(
    private val saveContactUseCase: SaveContactUseCase
) : BaseViewModel<QrEvent, QrEffect, QrState>() {

    private fun createContact(contact: Contact) {
        viewModelScope.launch {
            saveContactUseCase.start(contact)
                .onStart { setState { copy(viewState = ViewState.Loading) } }
                .catch { setStateError(it.message.toString()) }
                .collect {
                    setState { copy(viewState = ViewState.Success) }
                    Log.d("createContact", contact.toString())
                }
        }
    }

    override fun createInitialState(): QrState {
        return QrState(
            viewState = ViewState.Idle
        )
    }

    override fun handleEvent(event: QrEvent) {
        when (event) {
            is QrEvent.OnQrScan -> createContact(event.contact)
        }
    }

    private fun setStateError(message: String) {
        setState {
            copy(
                viewState = ViewState.Error
            )
        }
        setEffect { QrEffect.Error(message) }
    }
}
package com.sweeftdigital.contactsexchange.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.R
import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.domain.use_case.SaveContactUseCase
import dev.arch3rtemp.core_ui.base.BaseViewModel
import dev.arch3rtemp.core_ui.util.StringResourceManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val saveContactUseCase: SaveContactUseCase,
    private val resourceManager: StringResourceManager
) : BaseViewModel<MainEvent, MainEffect, MainState>() {

    override fun createInitialState(): MainState {
        return MainState // dummy
    }

    override fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnQrScanComplete -> createContact(event.contact)
            MainEvent.OnQrScanCanceled -> showMessage(resourceManager.string(R.string.msg_scan_cancelled))
            is MainEvent.OnQrScanFail -> showMessage(event.message)
        }
    }

    private fun createContact(contact: Contact) {
        viewModelScope.launch {
            saveContactUseCase.start(contact)
                .catch { showMessage(it.message) }
                .collectLatest {
                    showMessage(resourceManager.string(R.string.msg_contact_added))
                    Log.d("createContact", contact.toString())
                }
        }
    }

    private fun showMessage(message: String?) {
        setEffect { MainEffect.ShowMessage(message ?: resourceManager.string(R.string.msg_error_unspecified)) }
    }
}
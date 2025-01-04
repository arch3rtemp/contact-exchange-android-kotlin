package dev.arch3rtemp.contactexchange.presentation.ui

import androidx.lifecycle.viewModelScope
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.usecase.SaveContactUseCase
import dev.arch3rtemp.ui.base.BaseViewModel
import dev.arch3rtemp.ui.util.ErrorMsgResolver
import dev.arch3rtemp.ui.util.StringResourceManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel(
    private val saveContact: SaveContactUseCase,
    private val resourceManager: StringResourceManager,
    private val errorMsgResolver: ErrorMsgResolver
) : BaseViewModel<MainEvent, MainEffect, MainState>() {

    private val errorHandler = CoroutineExceptionHandler { context, throwable ->
        showMessage(throwable.localizedMessage)
    }

    override fun createInitialState(): MainState {
        return MainState // dummy
    }

    override fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnQrScanComplete -> createContact(event.contact)
            is MainEvent.OnQrScanCanceled -> showMessage(event.message)
            is MainEvent.OnQrScanFail -> showMessage(event.message)
        }
    }

    private fun createContact(contact: Contact) {
        viewModelScope.launch(errorHandler) {
            saveContact.invoke(contact)
            showMessage(resourceManager.string(R.string.msg_contact_added))
        }
    }

    private fun showMessage(message: String?) {
        setEffect { MainEffect.ShowMessage(errorMsgResolver.resolve(message)) }
    }
}

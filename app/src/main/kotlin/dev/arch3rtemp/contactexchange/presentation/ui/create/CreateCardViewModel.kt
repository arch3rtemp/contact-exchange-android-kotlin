package dev.arch3rtemp.contactexchange.presentation.ui.create

import androidx.lifecycle.viewModelScope
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.usecase.SaveContactUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.ValidateCardUseCase
import dev.arch3rtemp.ui.base.BaseViewModel
import dev.arch3rtemp.ui.util.ErrorMsgResolver
import dev.arch3rtemp.ui.util.StringResourceManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class CreateCardViewModel(
    private val saveContact: SaveContactUseCase,
    private val validateCard: ValidateCardUseCase,
    private val resourceManager: StringResourceManager,
    private val errorMsgResolver: ErrorMsgResolver
) : BaseViewModel<CreateCardEvent, CreateCardEffect, CreateCardState>() {

    private val errorHandler = CoroutineExceptionHandler { context, throwable ->
        setEffect { CreateCardEffect.ShowError(errorMsgResolver.resolve(throwable.localizedMessage)) }
    }

    override fun createInitialState(): CreateCardState {
        return CreateCardState // dummy
    }

    private fun saveCard(contact: Contact) {
        viewModelScope.launch(errorHandler) {
            if (validateCard(contact)) {
                saveContact(contact)
                setEffect { CreateCardEffect.NavigateUp }
            } else {
                setEffect { CreateCardEffect.ShowError(resourceManager.string(R.string.msg_all_fields_required)) }
            }
        }
    }

    override fun handleEvent(event: CreateCardEvent) {
        when(event) {
            is CreateCardEvent.OnCreateButtonPress -> { saveCard(event.contact) }
        }
    }
}

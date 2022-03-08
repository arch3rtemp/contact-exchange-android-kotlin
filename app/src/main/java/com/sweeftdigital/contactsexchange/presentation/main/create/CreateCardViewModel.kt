package com.sweeftdigital.contactsexchange.presentation.main.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.useCases.SaveContactUseCase
import com.sweeftdigital.contactsexchange.util.SingleLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateCardViewModel(
    private val saveContactUseCase: SaveContactUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    private val _state = SingleLiveData<CreateCardViewState>()
    val state: SingleLiveData<CreateCardViewState>
        get() = _state

    fun saveCard(contact: Contact) {
        viewModelScope.launch {
            if (checkContactNotBlank(contact)) {
                saveContactUseCase.start(contact)
                _state.value = CreateCardViewState(success = true)
            } else {
                _state.value = CreateCardViewState(error = true)
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
}
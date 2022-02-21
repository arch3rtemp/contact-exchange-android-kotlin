package com.sweeftdigital.contactsexchange.presentation.main.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.useCases.SaveContactUseCase
import com.sweeftdigital.contactsexchange.util.SingleLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateCardViewModel(
    private val saveContactUseCase: SaveContactUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    private val _error = SingleLiveData<CreateCardViewState>()
    val error: SingleLiveData<CreateCardViewState> get() = _error

    fun saveCard(contact: Contact) {
        viewModelScope.launch {
            if (checkContactNotBlank(contact)) {
                saveContactUseCase.start(contact)
            } else {
                _error.value = CreateCardViewState(error = "Fill all fields!")
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
package com.sweeftdigital.contactsexchange.presentation.main.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.useCases.SaveContactUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateCardViewModel(
    private val saveContactUseCase: SaveContactUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    private val _contactLiveData = MutableLiveData(CreateCardViewState())
    val contactLiveData: LiveData<CreateCardViewState> get() = _contactLiveData

    private fun checkContact(contact: Contact) {
        for ()
    }

    fun saveCard(contact: Contact) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                saveContactUseCase.start(contact)
            }
        }
    }
}
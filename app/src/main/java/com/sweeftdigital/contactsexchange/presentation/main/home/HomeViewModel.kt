package com.sweeftdigital.contactsexchange.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.app.db.AppDatabase
import com.sweeftdigital.contactsexchange.app.db.daos.ContactDao
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.useCases.DeleteContactUseCase
import com.sweeftdigital.contactsexchange.domain.useCases.SelectAllContactsUseCase
import com.sweeftdigital.contactsexchange.domain.useCases.SelectMyContactsUseCase
import com.sweeftdigital.contactsexchange.domain.useCases.SelectScannedContactsUseCase
import com.sweeftdigital.contactsexchange.util.toContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val selectMyContactsUseCase: SelectMyContactsUseCase,
    private val selectScannedContactsUseCase: SelectScannedContactsUseCase,
    private val deleteContactUseCase: DeleteContactUseCase
) : ViewModel() {
    private val _state = MutableLiveData<HomeViewState>()
    val state: LiveData<HomeViewState> get() = _state

    init {
        loadMyContacts()
        loadScannedContacts()
    }

    private fun loadMyContacts() {
        viewModelScope.launch {
            selectMyContactsUseCase.start().collect() {
                _state.value = HomeViewState.CardsState(
                    it
                )
            }
        }
    }

    private fun loadScannedContacts() {
        viewModelScope.launch {
            selectScannedContactsUseCase.start().collect {
                _state.value = HomeViewState.ContactsState(
                    it
                )
            }
        }
    }

    fun deleteContact(id: Int) {
        viewModelScope.launch {
            deleteContactUseCase.start(id)
        }
    }
}
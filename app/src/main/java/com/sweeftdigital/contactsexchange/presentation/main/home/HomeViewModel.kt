package com.sweeftdigital.contactsexchange.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.app.db.AppDatabase
import com.sweeftdigital.contactsexchange.app.db.daos.ContactDao
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.useCases.*
import com.sweeftdigital.contactsexchange.util.toContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val selectMyContactsUseCase: SelectMyContactsUseCase,
    private val selectScannedContactsUseCase: SelectScannedContactsUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val saveContactUseCase: SaveContactUseCase
) : ViewModel() {
    private val _state = MutableLiveData<HomeViewState>()
    val state: LiveData<HomeViewState> get() = _state

    private val _effect: Channel<HomeViewState.Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    fun getNewState(): LiveData<HomeViewState> {
        viewModelScope.launch {
            selectMyContactsUseCase.start()
                .onStart { _state.value = HomeViewState.Loading }
                .catch { _effect.send(HomeViewState.Effect.Error(it.message.toString())) }
                .collect { cards ->
                    selectScannedContactsUseCase.start()
                        .catch { _effect.send(HomeViewState.Effect.Error(it.message.toString())) }
                        .collect { contacts ->
                            _state.value = HomeViewState.Success(
                                myCards = cards,
                                contacts = contacts
                            )
                        }
                }
        }
        return state
    }

    fun saveContact(contact: Contact) {
        viewModelScope.launch {
            saveContactUseCase.start(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            deleteContactUseCase.start(contact.id)
                .catch { _effect.send(HomeViewState.Effect.Error(it.message.toString())) }
                .collect {
                    _effect.send(HomeViewState.Effect.Deleted(contact))
                }
        }
    }
}
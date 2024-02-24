package com.sweeftdigital.contactsexchange.presentation.home

import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.domain.use_case.DeleteContactUseCase
import com.sweeftdigital.contactsexchange.domain.use_case.SaveContactUseCase
import com.sweeftdigital.contactsexchange.domain.use_case.SelectMyContactsUseCase
import com.sweeftdigital.contactsexchange.domain.use_case.SelectScannedContactsUseCase
import com.sweeftdigital.contactsexchange.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val selectMyContactsUseCase: SelectMyContactsUseCase,
    private val selectScannedContactsUseCase: SelectScannedContactsUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val saveContactUseCase: SaveContactUseCase
) : BaseViewModel<HomeEvent, HomeEffect, HomeState>() {

    override fun createInitialState(): HomeState {
        return HomeState(cardsState = ViewState.Empty, contactsState = ViewState.Empty)
    }

    init {
        setEvent(HomeEvent.OnContactsLoad)
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnContactsLoad -> getNewState()
            is HomeEvent.OnContactDeleted -> deleteContact(event.contact)
            is HomeEvent.OnContactSaved -> saveContact(event.contact)
            is HomeEvent.OnSearchTyped -> setState { copy(query = event.query) }
        }
    }

    private fun getNewState() {
        getCards()
        getContacts()
    }

    private fun getCards() {
        viewModelScope.launch {
            selectMyContactsUseCase.start()
                .onStart { setState { copy(cardsState = ViewState.Loading) } }
                .catch {
                    setStateError(it.message.toString())
                }
                .collect { cards ->
                    if (cards.isNotEmpty()) {
                        setState { copy(cardsState = ViewState.Success(cards)) }
                    } else {
                        setState { copy(cardsState = ViewState.Empty) }
                    }
                }
        }
    }

    private fun getContacts() {
        viewModelScope.launch {
            selectScannedContactsUseCase.start()
                .onStart { setState { copy(contactsState = ViewState.Loading) } }
                .catch {
                    setStateError(it.message.toString())
                }
                .collect { contacts ->
                    if (contacts.isNotEmpty()) {
                        setState { copy(contactsState = ViewState.Success(contacts)) }
                    } else {
                        setState { copy(contactsState = ViewState.Empty) }
                    }
                }
        }
    }

    private fun saveContact(contact: Contact) {
        viewModelScope.launch {
            saveContactUseCase.start(contact)
                .catch { setStateError(it.message.toString()) }
                .collect()
        }
    }

    private fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            deleteContactUseCase.start(contact.id)
                .catch { setStateError(it.message.toString()) }
                .collect {
                    setEffect { HomeEffect.Deleted(contact) }
                }
        }
    }

    private fun setStateError(message: String) {
        setState { copy(cardsState = ViewState.Error, contactsState = ViewState.Error) }
        setEffect { HomeEffect.Error(message) }
    }
}
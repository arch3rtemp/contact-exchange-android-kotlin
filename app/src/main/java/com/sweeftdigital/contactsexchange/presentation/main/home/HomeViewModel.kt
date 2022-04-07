package com.sweeftdigital.contactsexchange.presentation.main.home

import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.useCases.DeleteContactUseCase
import com.sweeftdigital.contactsexchange.domain.useCases.SaveContactUseCase
import com.sweeftdigital.contactsexchange.domain.useCases.SelectMyContactsUseCase
import com.sweeftdigital.contactsexchange.domain.useCases.SelectScannedContactsUseCase
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
        return HomeState(
            cardsState = CardsState(viewState = ViewState.Empty),
            contactsState = ContactsState(viewState = ViewState.Empty)
        )
    }

    init {
        setEvent(HomeEvent.OnContactsLoaded)
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnContactsLoaded -> getNewState()
            is HomeEvent.OnContactDeleted -> deleteContact(event.contact)
            is HomeEvent.OnContactSaved -> saveContact(event.contact)
            is HomeEvent.OnSearchTyped -> setEffect { HomeEffect.Searched(event.searched) }
        }
    }

    private fun getNewState() {
        getCards()
        getContacts()
    }

    private fun getCards() {
        viewModelScope.launch {
            selectMyContactsUseCase.start()
                .onStart { setState { copy(cardsState = CardsState(viewState = ViewState.Loading)) } }
                .catch {
                    setStateError(it.message.toString())
                }
                .collect { cards ->
                    if (cards.isNotEmpty()) {
                        setState {
                            copy(
                                cardsState = CardsState(
                                    viewState = ViewState.Success,
                                    myCards = cards
                                )
                            )
                        }
                    } else {
                        setState { copy(cardsState = CardsState(viewState = ViewState.Empty)) }
                    }
                }
        }
    }

    private fun getContacts() {
        viewModelScope.launch {
            selectScannedContactsUseCase.start()
                .onStart { setState { copy(contactsState = ContactsState(viewState = ViewState.Loading)) } }
                .catch {
                    setStateError(it.message.toString())
                }
                .collect { contacts ->
                    if (contacts.isNotEmpty()) {
                        setState {
                            copy(
                                contactsState = ContactsState(
                                    viewState = ViewState.Success,
                                    contacts = contacts
                                )
                            )
                        }
                    } else {
                        setState { copy(contactsState = ContactsState(viewState = ViewState.Empty)) }
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
        setState { copy(
            cardsState = CardsState(viewState = ViewState.Error),
            contactsState = ContactsState(viewState = ViewState.Error)
        ) }
        setEffect { HomeEffect.Error(message) }
    }
}
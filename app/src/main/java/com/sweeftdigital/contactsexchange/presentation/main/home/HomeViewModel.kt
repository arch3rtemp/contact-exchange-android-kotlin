package com.sweeftdigital.contactsexchange.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.useCases.*
import com.sweeftdigital.contactsexchange.presentation.base.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

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
                    setState {
                        copy(
                            cardsState = CardsState(
                                viewState = ViewState.Success,
                                myCards = cards
                            )
                        )
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
                    setState {
                        copy(
                            contactsState = ContactsState(
                                viewState = ViewState.Success,
                                contacts = contacts
                            )
                        )
                    }
                }
        }
    }

    fun saveContact(contact: Contact) {
        viewModelScope.launch {
            saveContactUseCase.start(contact)
        }
    }

    fun deleteContact(contact: Contact) {
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

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnAddPressed -> TODO()
            HomeEvent.OnCardPressed -> TODO()
            HomeEvent.OnContactPressed -> TODO()
            HomeEvent.OnContactsLoaded -> getNewState()
        }
    }


}
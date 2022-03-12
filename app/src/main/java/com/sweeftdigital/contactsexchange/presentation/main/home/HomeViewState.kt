package com.sweeftdigital.contactsexchange.presentation.main.home

import com.sweeftdigital.contactsexchange.domain.models.Contact

sealed class HomeViewState {
    data class CardsState(val myCards: List<Contact> = listOf()) : HomeViewState()
    data class ContactsState(val contacts: List<Contact> = listOf()) : HomeViewState()
}
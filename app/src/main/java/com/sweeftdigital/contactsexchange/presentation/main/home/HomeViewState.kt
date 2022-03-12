package com.sweeftdigital.contactsexchange.presentation.main.home

import com.sweeftdigital.contactsexchange.domain.models.Contact

sealed class HomeViewState {

    object Loading : HomeViewState()

    sealed class Effect {
        data class Error(val message: String) : Effect()
        data class Deleted(val contact: Contact) : Effect()
    }

    data class Success(
        val myCards: List<Contact> = listOf(),
        val contacts: List<Contact> = listOf()
    ) : HomeViewState()
}
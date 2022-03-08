package com.sweeftdigital.contactsexchange.presentation.main.home

import com.sweeftdigital.contactsexchange.domain.models.Contact

data class HomeViewState(
    val myCards: List<Contact> = listOf(),
    val contacts: List<Contact> = listOf()
)
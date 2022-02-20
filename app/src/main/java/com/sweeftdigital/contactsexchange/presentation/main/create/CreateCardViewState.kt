package com.sweeftdigital.contactsexchange.presentation.main.create

import com.sweeftdigital.contactsexchange.domain.models.Contact
import java.lang.Exception

data class CreateCardViewState(
    val contact: Contact? = null,
    val error: String = ""
)
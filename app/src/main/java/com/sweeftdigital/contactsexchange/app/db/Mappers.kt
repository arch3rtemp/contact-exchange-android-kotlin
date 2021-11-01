package com.sweeftdigital.contactsexchange.app.db

import com.sweeftdigital.contactsexchange.app.db.models.ContactEntity
import com.sweeftdigital.contactsexchange.domain.models.Contact

internal fun ContactEntity.toContact(): Contact {
    return Contact(
        id,
        firstName,
        lastName,
        job,
        position,
        email,
        phoneMobile,
        phoneOffice,
        createDate,
        color,
        isMy
    )
}
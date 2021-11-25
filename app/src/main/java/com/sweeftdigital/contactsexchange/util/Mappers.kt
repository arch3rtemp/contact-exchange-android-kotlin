package com.sweeftdigital.contactsexchange.util

import com.sweeftdigital.contactsexchange.app.db.models.ContactEntity
import com.sweeftdigital.contactsexchange.domain.models.Contact

internal fun ContactEntity.toContact(): Contact {
    return Contact(
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

internal fun Contact.toContactEntity(): ContactEntity {
    return ContactEntity(
        firstName = firstName,
        lastName = lastName,
        job = job,
        position = position,
        email = email,
        phoneMobile = phoneMobile,
        phoneOffice = phoneOffice,
        createDate = createDate,
        color = color,
        isMy = isMy
    )
}
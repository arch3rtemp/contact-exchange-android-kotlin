package com.sweeftdigital.contactsexchange.util

import com.sweeftdigital.contactsexchange.data.db.models.ContactEntity
import com.sweeftdigital.contactsexchange.domain.models.Contact

internal fun ContactEntity.toContact(): Contact {
    return Contact(
        id,
        name,
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
        id = id,
        name = name,
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
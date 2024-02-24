package com.sweeftdigital.contactsexchange.data.mapper

import com.sweeftdigital.contactsexchange.data.db.model.ContactEntity
import com.sweeftdigital.contactsexchange.domain.model.Contact
import com.sweeftdigital.contactsexchange.domain.model.ContactType
import org.json.JSONObject
import java.util.Date

internal fun entityToContact(entity: ContactEntity): Contact {
    return Contact(
        id = entity.id,
        name = entity.name,
        job = entity.job,
        position = entity.position,
        email = entity.email,
        phoneMobile = entity.phoneMobile,
        phoneOffice = entity.phoneOffice,
        createDate = entity.createDate,
        color = entity.color,
        isMy = ContactType.entries[entity.isMy]
    )
}

internal fun contactToEntity(contact: Contact): ContactEntity {
    return ContactEntity(
        id = contact.id,
        name = contact.name,
        job = contact.job,
        position = contact.position,
        email = contact.email,
        phoneMobile = contact.phoneMobile,
        phoneOffice = contact.phoneOffice,
        createDate = contact.createDate,
        color = contact.color,
        isMy = contact.isMy.ordinal
    )
}

internal fun jsonToContact(jsonData: JSONObject): Contact {
    return Contact(
        name = jsonData.getString("name"),
        job = jsonData.getString("job"),
        position = jsonData.getString("position"),
        email = jsonData.getString("email"),
        phoneMobile = jsonData.getString("phoneMobile"),
        phoneOffice = jsonData.getString("phoneOffice"),
        createDate = Date(),
        color = jsonData.getInt("color"),
        isMy = ContactType.NOT_MY_CARD
    )
}
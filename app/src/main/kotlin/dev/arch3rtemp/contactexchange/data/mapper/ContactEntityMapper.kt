package dev.arch3rtemp.contactexchange.data.mapper

import dev.arch3rtemp.contactexchange.data.db.model.ContactEntity
import dev.arch3rtemp.contactexchange.domain.model.Contact

class ContactEntityMapper() {

    fun fromEntity(entity: ContactEntity): Contact {
        return Contact(
            id = entity.id,
            name = entity.name,
            job = entity.job,
            position = entity.position,
            email = entity.email,
            phoneMobile = entity.phoneMobile,
            phoneOffice = entity.phoneOffice,
            createdAt = entity.createdAt,
            color = entity.color,
            isMy = entity.isMy
        )
    }

    fun toEntity(contact: Contact): ContactEntity {
        return ContactEntity(
            id = contact.id,
            name = contact.name,
            job = contact.job,
            position = contact.position,
            email = contact.email,
            phoneMobile = contact.phoneMobile,
            phoneOffice = contact.phoneOffice,
            createdAt = contact.createdAt,
            color = contact.color,
            isMy = contact.isMy
        )
    }

    fun fromEntityList(entities: List<ContactEntity>): List<Contact> {
        return entities.map { entity ->
            fromEntity(entity)
        }
    }

    fun toEntityList(contacts: List<Contact>): List<ContactEntity> {
        return contacts.map { contact ->
            toEntity(contact)
        }
    }
}

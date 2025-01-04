package dev.arch3rtemp.contactexchange.presentation.mapper

import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.presentation.model.ContactUi
import dev.arch3rtemp.ui.util.TimeConverter
import java.time.ZoneId
import java.util.Locale

class ContactUiMapper(
    private val timeConverter: TimeConverter
) {

    fun fromUiModel(uiModel: ContactUi): Contact {
        return Contact(
            id = uiModel.id,
            name = uiModel.name,
            job = uiModel.job,
            position = uiModel.position,
            email = uiModel.email,
            phoneMobile = uiModel.phoneMobile,
            phoneOffice = uiModel.phoneOffice,
            createdAt = uiModel.createdAt,
            color = uiModel.color,
            isMy = uiModel.isMy
        )
    }

    fun toUiModel(contact: Contact): ContactUi {
        return ContactUi(
            id = contact.id,
            name = contact.name,
            job = contact.job,
            position = contact.position,
            email = contact.email,
            phoneMobile = contact.phoneMobile,
            phoneOffice = contact.phoneOffice,
            createdAt = contact.createdAt,
            formattedCreatedAt = timeConverter.convertLongToDateString(
                contact.createdAt,
                DATE_PATTERN,
                Locale.getDefault(),
                ZoneId.systemDefault().toString()
            ),
            color = contact.color,
            isMy = contact.isMy
        )
    }

    fun fromUiList(uiModels: List<ContactUi>): List<Contact> {
        return uiModels.map { uiModel ->
            fromUiModel(uiModel)
        }
    }

    fun toUiList(contacts: List<Contact>): List<ContactUi> {
        return contacts.map { contacts ->
            toUiModel(contacts)
        }
    }

    companion object {
        private const val DATE_PATTERN = "dd MMM yy"
    }
}

package dev.arch3rtemp.contactexchange.presentation.mapper

import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.ui.util.TimeConverter
import kotlin.test.Test
import kotlin.test.assertEquals

class ContactUiMapperTest {

    private val mapper = ContactUiMapper(TimeConverter())

    @Test
    fun invokeFromUiModel_returnsContactUi() {
        val contact = mapper.fromUiModel(TestData.testScannedContactUi)

        assertEquals(TestData.testScannedContact, contact)
    }

    @Test
    fun invokeToUiModel_returnsContactUi() {
        val contactUi = mapper.toUiModel(TestData.testScannedContact)

        assertEquals(TestData.testScannedContactUi, contactUi)
    }

    @Test
    fun invokeFromUiList_returnsContactList() {
        val contactList = mapper.fromUiList(TestData.testContactsUi)

        assertEquals(TestData.testContacts, contactList)
    }

    @Test
    fun invokeToUiList_returnsContactUiList() {
        val contactUiList = mapper.toUiList(TestData.testContacts)

        assertEquals(TestData.testContactsUi, contactUiList)
    }
}

package dev.arch3rtemp.contactexchange.presentation.mapper

import dev.arch3rtemp.contactexchange.TestData
import kotlin.test.Test
import kotlin.test.assertEquals

class ContactToJsonMapperTest {

    private val mapper = ContactToJsonMapper()

    @Test
    fun invokeToJson_returnsValidString() {
        val jsonContact = mapper.toJson(TestData.testMyContactUi)

        assertEquals(TestData.testContactUiJsonCompact, jsonContact)
    }
}

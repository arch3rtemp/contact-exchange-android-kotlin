package dev.arch3rtemp.contactexchange.data.mapper

import dev.arch3rtemp.contactexchange.TestData
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse

class JsonToContactMapperTest {

    private val mapper = JsonToContactMapper()

    @Test
    fun invokeFromJson_withValidData_returnsContact() {
        val result = mapper.fromJson(TestData.testContactJson)

        assertEquals(0, result.id)
        assertEquals(TestData.testMyContact.name, result.name)
        assertEquals(TestData.testMyContact.job, result.job)
        assertEquals(TestData.testMyContact.position, result.position)
        assertEquals(TestData.testMyContact.email, result.email)
        assertEquals(TestData.testMyContact.phoneMobile, result.phoneMobile)
        assertEquals(TestData.testMyContact.phoneOffice, result.phoneOffice)
        assertEquals(TestData.testMyContact.color, result.color)
        assertFalse(result.isMy)
    }

    @Test
    fun invokeFromJson_withInvalidData_throwsJSONException() {
        assertFails {
            mapper.fromJson("invalid string")
        }
    }
}

package dev.arch3rtemp.contactexchange.data.mapper

import dev.arch3rtemp.contactexchange.TestData
import kotlin.test.Test
import kotlin.test.assertEquals

class ContactEntityMapperTest {

    private val mapper = ContactEntityMapper()

    @Test
    fun invokeFromEntity_returnsContact() {
        val result = mapper.fromEntity(TestData.testMyContactEntity)
        assertEquals(TestData.testMyContact, result)
    }

    @Test
    fun invokeToEntity_returnsContactEntity() {
        val result = mapper.toEntity(TestData.testMyContact)
        assertEquals(TestData.testMyContactEntity, result)
    }

    @Test
    fun invokeFromEntityList_returnsContactList() {
        val result = mapper.fromEntityList(TestData.testContactsEntity)
        assertEquals(TestData.testContacts, result)
    }

    @Test
    fun invokeToEntityList_returnsCardEntityList() {
        val result = mapper.toEntityList(TestData.testContacts)
        assertEquals(TestData.testContactsEntity, result)
    }
}

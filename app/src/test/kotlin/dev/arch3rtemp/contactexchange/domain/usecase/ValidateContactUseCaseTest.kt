package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.domain.model.Contact
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidateContactUseCaseTest {

    private lateinit var validateContact: ValidateContactUseCase

    @BeforeTest
    fun setUp() {
        validateContact = ValidateContactUseCase()
    }

    @Test
    fun invoke_validateContact_WithValidCard() {
        val result = validateContact(TestData.testMyContact)

        assertTrue(result)
    }

    @Test
    fun invoke_validateContact_WithBlankCard() {
        val result = validateContact(Contact())

        assertFalse(result)
    }
}

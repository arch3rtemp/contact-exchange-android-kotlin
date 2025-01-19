package dev.arch3rtemp.contactexchange.domain.usecase

import android.database.sqlite.SQLiteException
import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.data.repository.ContactRepositoryImpl
import dev.arch3rtemp.ui.R
import dev.arch3rtemp.ui.util.StringResourceManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetContactByIdUseCaseTest {

    @MockK
    private lateinit var mockRepository: ContactRepositoryImpl

    @MockK
    private lateinit var mockStringManager: StringResourceManager

    @InjectMockKs
    private lateinit var getContactById: GetContactByIdUseCase

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invokeWithValidId_completes() = runTest {
        coEvery { mockRepository.getContactById(TestData.testMyContact.id) } returns TestData.testMyContact

        val contact = getContactById(TestData.testMyContact.id)

        assertEquals(TestData.testMyContact, contact)
        coVerify(exactly = 1) { mockRepository.getContactById(TestData.testMyContact.id) }
    }

    @Test
    fun invokeWithInvalidId_throwsError() = runTest {
        every { mockStringManager.string(R.string.msg_id_must_be_positive) } returns "ID must be positive"

        assertFailsWith<IllegalArgumentException>("ID must be positive") { getContactById(TestData.NEGATIVE_CONTACT_ID) }

        coVerify(exactly = 0) { mockRepository.getContactById(TestData.NEGATIVE_CONTACT_ID) }
        verify { mockStringManager.string(R.string.msg_id_must_be_positive) }
    }

    @Test
    fun invokeWithZeroId_throwsError() = runTest {
        every { mockStringManager.string(R.string.msg_id_must_be_positive) } returns "ID must be positive"

        assertFailsWith<IllegalArgumentException>("ID must be positive") { getContactById(TestData.ZERO_CONTACT_ID) }

        coVerify(exactly = 0) { mockRepository.getContactById(TestData.ZERO_CONTACT_ID) }
        verify { mockStringManager.string(R.string.msg_id_must_be_positive) }
    }

    @Test
    fun invokeWithValidId_repositoryFails() = runTest {
        coEvery { mockRepository.getContactById(TestData.testMyContact.id) } throws TestData.sqlException

        assertFailsWith<SQLiteException> { getContactById(TestData.testMyContact.id) }

        coVerify(exactly = 1) { mockRepository.getContactById(TestData.testMyContact.id) }
    }
}

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
import kotlin.test.assertFailsWith

class DeleteContactUseCaseTest {

    @MockK
    private lateinit var mockRepository: ContactRepositoryImpl

    @MockK
    private lateinit var mockStringManager: StringResourceManager

    @InjectMockKs
    private lateinit var deleteContact: DeleteContactUseCase

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke_deleteContact_WithValidId_completes() = runTest {
        coEvery { mockRepository.deleteContact(TestData.testMyContact.id) } returns Unit

        deleteContact(TestData.testMyContact.id)

        coVerify(exactly = 1) { mockRepository.deleteContact(TestData.testMyContact.id) }
    }

    @Test
    fun invoke_deleteContact_WithInvalidId_throwsError() = runTest {
        every { mockStringManager.string(R.string.msg_id_must_be_positive) } returns "ID must be positive"

        assertFailsWith<IllegalArgumentException>("ID must be positive") { deleteContact(TestData.NEGATIVE_CONTACT_ID) }

        coVerify(exactly = 0) { mockRepository.deleteContact(any()) }
        verify(exactly = 1) { mockStringManager.string(R.string.msg_id_must_be_positive) }
    }

    @Test
    fun invokeWithZeroId_throwsError() = runTest {
        every { mockStringManager.string(R.string.msg_id_must_be_positive) } returns "ID must be positive"

        assertFailsWith<IllegalArgumentException>("ID must be positive") { deleteContact(TestData.ZERO_CONTACT_ID) }

        coVerify(exactly = 0) { mockRepository.deleteContact(any()) }
        verify(exactly = 1) { mockStringManager.string(R.string.msg_id_must_be_positive) }
    }

    @Test
    fun invokeWithValidId_repositoryFails() = runTest {
        coEvery { mockRepository.deleteContact(TestData.testMyContact.id) } throws TestData.sqlException

        assertFailsWith<SQLiteException> { deleteContact(TestData.testMyContact.id) }

        coVerify(exactly = 1) { mockRepository.deleteContact(TestData.testMyContact.id) }
    }
}

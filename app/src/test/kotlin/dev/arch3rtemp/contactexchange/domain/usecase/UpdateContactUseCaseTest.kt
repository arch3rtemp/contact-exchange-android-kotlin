package dev.arch3rtemp.contactexchange.domain.usecase

import android.database.sqlite.SQLiteException
import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.data.repository.ContactRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class UpdateContactUseCaseTest {

    @MockK
    private lateinit var mockRepository: ContactRepositoryImpl

    @InjectMockKs
    private lateinit var updateContact: UpdateContactUseCase

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke_updateContact_completes() = runTest {
        coEvery { mockRepository.updateContact(TestData.mergedContact) } returns Unit

        updateContact(TestData.testMyContact, TestData.testNewContact)

        coVerify(exactly = 1) { mockRepository.updateContact(TestData.mergedContact) }
    }

    @Test
    fun invoke_updateContact_throwsError() = runTest {
        coEvery { mockRepository.updateContact(TestData.mergedContact) } throws TestData.sqlException

        assertFailsWith<SQLiteException> { updateContact(TestData.testMyContact, TestData.testNewContact) }

        coVerify(exactly = 1) { mockRepository.updateContact(TestData.mergedContact) }
    }
}

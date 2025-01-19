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

class SaveContactUseCaseTest {

    @MockK
    private lateinit var mockRepository: ContactRepositoryImpl

    @InjectMockKs
    private lateinit var saveContact: SaveContactUseCase

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke_saveContact_completes() = runTest {
        coEvery { mockRepository.addContact(TestData.testMyContact) } returns Unit

        saveContact(TestData.testMyContact)

        coVerify(exactly = 1) { mockRepository.addContact(TestData.testMyContact) }
    }

    @Test
    fun invoke_saveContact_throwsError() = runTest {
        coEvery { mockRepository.addContact(TestData.testMyContact) } throws TestData.sqlException

        assertFailsWith<SQLiteException> { saveContact(TestData.testMyContact) }

        coVerify(exactly = 1) { mockRepository.addContact(TestData.testMyContact) }
    }
}

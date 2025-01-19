package dev.arch3rtemp.contactexchange.domain.usecase

import android.database.sqlite.SQLiteException
import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.data.repository.ContactRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetScannedContactsUseCaseTest {

    @MockK
    private lateinit var mockRepository: ContactRepositoryImpl

    @InjectMockKs
    private lateinit var getScannedContacts: GetScannedContactsUseCase

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke_invokeGetScannedContacts_returnValidData() = runTest {
        coEvery { mockRepository.getScannedContacts() } returns flowOf(TestData.testContacts)

        val results = getScannedContacts().toList()

        assertEquals(TestData.testContacts, results.first())

        coVerify { mockRepository.getScannedContacts() }
    }

    @Test
    fun invoke_invokeGetScannedContacts_failsRepository() = runTest {
        coEvery { mockRepository.getScannedContacts() } returns flow { throw TestData.sqlException }

        assertFailsWith<SQLiteException> { getScannedContacts().toList() }

        coVerify { mockRepository.getScannedContacts() }
    }
}

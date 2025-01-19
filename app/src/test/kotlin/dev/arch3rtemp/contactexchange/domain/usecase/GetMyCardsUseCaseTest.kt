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

class GetMyCardsUseCaseTest {

    @MockK
    private lateinit var mockRepository: ContactRepositoryImpl

    @InjectMockKs
    private lateinit var getMyCards: GetMyCardsUseCase

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke_getMyCards_returnsValidData() = runTest {
        coEvery { mockRepository.getMyContacts() } returns flowOf(TestData.testContacts)

        val results = getMyCards().toList()

        assertEquals(TestData.testContacts, results.first())

        coVerify { mockRepository.getMyContacts() }
    }

    @Test
    fun invoke_getMyCards_failsRepository() = runTest {
        coEvery { mockRepository.getMyContacts() } returns flow { throw TestData.sqlException }

        assertFailsWith<SQLiteException> { getMyCards().toList() }

        coVerify { mockRepository.getMyContacts() }
    }
}

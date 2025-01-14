package dev.arch3rtemp.contactexchange.data.repository

import android.database.sqlite.SQLiteException
import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.data.dataprovider.local.LocalDataProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ContactRepositoryImplTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var mockLocalDataProvider: LocalDataProvider

    @InjectMockKs
    lateinit var repository: ContactRepositoryImpl

    @Test
    fun invokeGetMyContacts_returnsValidData() = runTest {
        coEvery { mockLocalDataProvider.selectMyContacts() } returns flowOf(TestData.testContacts)

        val contacts = repository.getMyContacts().first()

        coVerify { mockLocalDataProvider.selectMyContacts() }

        assertEquals(TestData.testContacts, contacts)
    }

    @Test
    fun invokeGetMyContacts_failsDb() = runTest {
        coEvery { mockLocalDataProvider.selectMyContacts() } returns flow { throw TestData.sqlException }

        // Directly use assertFailsWith in the outer runTest block
        assertFailsWith<SQLiteException> { repository.getMyContacts().first() }

        coVerify { mockLocalDataProvider.selectMyContacts() }
    }

    @Test
    fun invokeGetScannedContacts_returnsValidData() = runTest {
        coEvery { mockLocalDataProvider.selectScannedContacts() } returns flowOf(TestData.testContacts)

        val contacts = repository.getScannedContacts().first()

        coVerify { mockLocalDataProvider.selectScannedContacts() }

        assertEquals(TestData.testContacts, contacts)
    }

    @Test
    fun invokeGetScannedContacts_failsDb() = runTest {
        coEvery { mockLocalDataProvider.selectScannedContacts() } returns flow { throw TestData.sqlException }

        assertFailsWith<SQLiteException> { repository.getScannedContacts().first() }

        coVerify { mockLocalDataProvider.selectScannedContacts() }
    }

    @Test
    fun invokeGetContactById_returnsValidData() = runTest {
        coEvery {
            mockLocalDataProvider.selectContactById(TestData.testMyContact.id)
        } returns TestData.testMyContact

        val contact = repository.getContactById(TestData.testMyContact.id)

        coVerify { mockLocalDataProvider.selectContactById(TestData.testMyContact.id) }

        assertEquals(TestData.testMyContact, contact)
    }

    @Test
    fun invokeGetContactById_failsDb() = runTest {
        coEvery { mockLocalDataProvider.selectContactById(TestData.testMyContact.id) } throws TestData.sqlException

        assertFailsWith<SQLiteException> { repository.getContactById(TestData.testMyContact.id) }

        coVerify { mockLocalDataProvider.selectContactById(TestData.testMyContact.id) }
    }

    @Test
    fun invokeAddContact_withValidContact_completesSuccessfully() = runTest {
        coEvery { mockLocalDataProvider.addContact(TestData.testScannedContact) } returns Unit

        repository.addContact(TestData.testScannedContact)

        coVerify { mockLocalDataProvider.addContact(TestData.testScannedContact) }
    }

    @Test
    fun invokeAddContact_failsDb() = runTest {
        coEvery { mockLocalDataProvider.addContact(TestData.testScannedContact) } throws TestData.sqlException

        assertFailsWith<SQLiteException> { repository.addContact(TestData.testScannedContact) }

        coVerify { mockLocalDataProvider.addContact(TestData.testScannedContact) }
    }

    @Test
    fun invokeUpdateContact_withValidContact_completesSuccessfully() = runTest {
        coEvery { mockLocalDataProvider.updateContact(TestData.testScannedContact) } returns Unit

        repository.updateContact(TestData.testScannedContact)

        coVerify { mockLocalDataProvider.updateContact(TestData.testScannedContact) }
    }

    @Test
    fun invokeUpdateContact_failsDb() = runTest {
        coEvery { mockLocalDataProvider.updateContact(TestData.testScannedContact) } throws TestData.sqlException

        assertFailsWith<SQLiteException> { repository.updateContact(TestData.testScannedContact) }

        coVerify { mockLocalDataProvider.updateContact(TestData.testScannedContact) }
    }

    @Test
    fun invokeDeleteContact_withValidId_completesSuccessfully() = runTest {
        coEvery { mockLocalDataProvider.deleteContact(TestData.testMyContact.id) } returns Unit

        repository.deleteContact(TestData.testMyContact.id)

        coVerify { mockLocalDataProvider.deleteContact(TestData.testMyContact.id) }
    }

    @Test
    fun invokeDeleteContact_failsDb() = runTest {
        coEvery { mockLocalDataProvider.deleteContact(TestData.testMyContact.id) } throws TestData.sqlException

        assertFailsWith<SQLiteException> { repository.deleteContact(TestData.testMyContact.id) }

        coVerify { mockLocalDataProvider.deleteContact(TestData.testMyContact.id) }
    }
}

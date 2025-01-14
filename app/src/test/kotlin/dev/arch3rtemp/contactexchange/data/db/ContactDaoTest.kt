package dev.arch3rtemp.contactexchange.data.db

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.data.db.dao.ContactDao
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ContactDaoTest {

    private lateinit var testDb: AppDatabase
    private lateinit var contactDao: ContactDao

    @Before
    fun setUp() = runTest {
        testDb = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        contactDao = testDb.contactDao()
        initDb()
    }

    @After
    fun tearDown() {
        testDb.close()
    }

    private suspend fun initDb() {
        contactDao.insert(TestData.testScannedContactEntity)
        contactDao.insert(TestData.testMyContactEntity)
    }

    @Test
    fun selectScannedContacts_returnsValidData() = runTest {
        // Collect the first emission from the Flow
        val contacts = contactDao.selectScannedContacts().first()

        // Assert that the retrieved data matches the expected test data
        assertNotNull("Contacts should not be null", contacts)
        assertEquals("Should retrieve exactly one scanned contact", 1, contacts.size)
        assertEquals("Retrieved contact should match the test scanned contact",
            TestData.testScannedContactEntity, contacts[0])
    }

    @Test
    fun selectMyContacts_returnsValidData() = runTest {
        val contacts = contactDao.selectMyContacts().first()

        assertNotNull("Contacts should not be null", contacts)
        assertEquals("Should retrieve exactly one my contact", 1, contacts.size)
        assertEquals("Retrieved contact should match the test my contact",
            TestData.testMyContactEntity, contacts[0])
    }

    @Test
    fun selectContactById_returnsValidData() = runTest {
        val contact = contactDao.selectContactById(TestData.testMyContactEntity.id)

        assertNotNull("Contact should not be null", contact)
        assertEquals("Retrieved contact should match the test my contact",
            TestData.testMyContactEntity, contact)
    }

    @Test
    fun updateAndRetrieve_returnsUpdatedContact() = runTest {
        // Update a contact entity
        val updatedContact = TestData.mergedContactEntity
        contactDao.update(updatedContact)

        // Retrieve the updated contact
        val contact = contactDao.selectContactById(updatedContact.id)

        // Assert that the contact was updated correctly
        assertNotNull("Updated contact should not be null", contact)
        assertEquals("Retrieved contact should match the updated contact",
            updatedContact, contact)
    }

    @Test
    fun deleteAndCheck_returnsNoData() = runTest {
        // Delete a contact entity
        contactDao.delete(TestData.testMyContactEntity.id)

        // Attempt to retrieve the deleted contact
        val contact = contactDao.selectContactById(TestData.testMyContactEntity.id)

        // Assert that the contact no longer exists
        assertNull("Contact should be null after deletion", contact)
    }
}

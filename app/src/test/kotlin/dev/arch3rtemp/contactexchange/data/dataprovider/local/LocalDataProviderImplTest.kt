package dev.arch3rtemp.contactexchange.data.dataprovider.local

import android.database.sqlite.SQLiteException
import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.data.db.dao.ContactDao
import dev.arch3rtemp.contactexchange.data.mapper.ContactEntityMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LocalDataProviderImplTest {

    @MockK
    lateinit var mockContactDao: ContactDao

    @MockK
    lateinit var mockMapper: ContactEntityMapper

    @InjectMockKs
    private lateinit var dataProvider: LocalDataProviderImpl

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invokeSelectMyContacts_mapsAndReturnsValidData() = runTest {
        every { mockContactDao.selectMyContacts() } returns flowOf(TestData.testContactsEntity)
        every { mockMapper.fromEntityList(TestData.testContactsEntity) } returns TestData.testContacts

        val contacts = dataProvider.selectMyContacts().first()

        verify(exactly = 1) { mockMapper.fromEntityList(TestData.testContactsEntity) }
        verify(exactly = 1) { mockContactDao.selectMyContacts() }

        assertEquals(TestData.testContacts, contacts)
    }

    @Test
    fun invokeSelectMyContacts_failsDb() = runTest {
        every { mockContactDao.selectMyContacts() } returns flow { throw TestData.sqlException }

        assertFailsWith<SQLiteException> { dataProvider.selectMyContacts().first() }

        verify(exactly = 1) { mockContactDao.selectMyContacts() }
    }

    @Test
    fun invokeSelectScannedContacts_mapsAndReturnsValidData() = runTest {
        every { mockContactDao.selectScannedContacts() } returns flowOf(TestData.testContactsEntity)
        every { mockMapper.fromEntityList(TestData.testContactsEntity) } returns TestData.testContacts

        val contacts = dataProvider.selectScannedContacts().first()

        assertEquals(TestData.testContacts, contacts)

        verify(exactly = 1) { mockMapper.fromEntityList(TestData.testContactsEntity) }
        verify(exactly = 1) { mockContactDao.selectScannedContacts() }
    }

    @Test
    fun invokeSelectScannedContacts_failsDb() = runTest {
        every { mockContactDao.selectScannedContacts() } returns flow { throw TestData.sqlException }

        assertFailsWith<SQLiteException> { dataProvider.selectScannedContacts().first() }

        verify(exactly = 1) { mockContactDao.selectScannedContacts() }
    }

    @Test
    fun invokeSelectContactById_mapsAndReturnsValidData() = runTest {
        coEvery { mockContactDao.selectContactById(TestData.testScannedContactEntity.id) } returns TestData.testScannedContactEntity
        every { mockMapper.fromEntity(TestData.testScannedContactEntity) } returns TestData.testScannedContact

        val contact = dataProvider.selectContactById(TestData.testScannedContactEntity.id)

        coVerify { mockContactDao.selectContactById(TestData.testScannedContactEntity.id) }
        verify { mockMapper.fromEntity(TestData.testScannedContactEntity) }

        assertEquals(TestData.testScannedContact, contact)
    }

    @Test
    fun invokeSelectContactById_failsDb() = runTest {
        coEvery { mockContactDao.selectContactById(TestData.testScannedContactEntity.id) } throws TestData.sqlException

        assertFailsWith<SQLiteException> { dataProvider.selectContactById(TestData.testScannedContactEntity.id) }

        coVerify { mockContactDao.selectContactById(TestData.testScannedContactEntity.id) }
    }

    @Test
    fun invokeAddContact_withValidContact_completesSuccessfully() = runTest {
        coEvery { mockContactDao.insert(TestData.testMyContactEntity) } returns Unit
        every { mockMapper.toEntity(TestData.testMyContact) } returns TestData.testMyContactEntity

        dataProvider.addContact(TestData.testMyContact)

        coVerify { mockContactDao.insert(TestData.testMyContactEntity) }
        verify { mockMapper.toEntity(TestData.testMyContact) }
    }

    @Test
    fun invokeAddContact_failsDb() = runTest {
        coEvery { mockContactDao.insert(TestData.testMyContactEntity) } throws TestData.sqlException
        every { mockMapper.toEntity(TestData.testMyContact) } returns TestData.testMyContactEntity

        assertFailsWith<SQLiteException> { dataProvider.addContact(TestData.testMyContact) }

        coVerify { mockContactDao.insert(TestData.testMyContactEntity) }
    }

    @Test
    fun invokeUpdateContact_withValidContact_completesSuccessfully() = runTest {
        coEvery { mockContactDao.update(TestData.testMyContactEntity) } returns Unit
        every { mockMapper.toEntity(TestData.testMyContact) } returns TestData.testMyContactEntity

        dataProvider.updateContact(TestData.testMyContact)

        coVerify { mockContactDao.update(TestData.testMyContactEntity) }
        verify { mockMapper.toEntity(TestData.testMyContact) }
    }

    @Test
    fun invokeUpdateContact_failsDb() = runTest {
        coEvery { mockContactDao.update(TestData.testMyContactEntity) } throws TestData.sqlException
        every { mockMapper.toEntity(TestData.testMyContact) } returns TestData.testMyContactEntity

        assertFailsWith<SQLiteException> { dataProvider.updateContact(TestData.testMyContact) }

        coVerify { mockContactDao.update(TestData.testMyContactEntity) }
        verify { mockMapper.toEntity(TestData.testMyContact) }
    }

    @Test
    fun invokeDeleteContact_withValidId_completesSuccessfully() = runTest {
        coEvery { mockContactDao.delete(TestData.testMyContactEntity.id) } returns Unit

        dataProvider.deleteContact(TestData.testMyContactEntity.id)

        coVerify { mockContactDao.delete(TestData.testMyContactEntity.id) }
    }

    @Test
    fun invokeDeleteContact_failsDb() = runTest {
        coEvery { mockContactDao.delete(TestData.testMyContactEntity.id) } throws TestData.sqlException

        assertFailsWith<SQLiteException> { mockContactDao.delete(TestData.testMyContactEntity.id) }

        coVerify { mockContactDao.delete(TestData.testMyContactEntity.id) }
    }
}

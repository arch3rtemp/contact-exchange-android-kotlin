package dev.arch3rtemp.contactexchange.presentation.ui

import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.coroutines.MainCoroutinesRule
import dev.arch3rtemp.contactexchange.domain.usecase.SaveContactUseCase
import dev.arch3rtemp.ui.R
import dev.arch3rtemp.ui.util.ErrorMsgResolver
import dev.arch3rtemp.ui.util.StringResourceManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutinesRule()

    @MockK
    private lateinit var mockSaveContact: SaveContactUseCase

    @MockK
    private lateinit var mockResourceManager: StringResourceManager

    @MockK
    private lateinit var mockErrorMsgResolver: ErrorMsgResolver

    @InjectMockKs
    private lateinit var mainViewModel: MainViewModel

    private val emittedEffects = mutableListOf<MainEffect>()

    private val effectJob = Job()

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)

        runTest {
            launch(effectJob) {
                mainViewModel.effect.collect { effect ->
                    emittedEffects.add(effect)
                }
            }
        }
    }

    @AfterTest
    fun tearDown() {
        effectJob.cancel()
        emittedEffects.clear()
    }

    @Test
    fun scanComplete_createContact_successfully() = runTest {
        coEvery { mockSaveContact(TestData.testScannedContact) } returns Unit
        every { mockResourceManager.string(R.string.msg_contact_added) } returns "Contact Added"

        mainViewModel.setEvent(MainEvent.OnQrScanComplete(TestData.testScannedContact))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockSaveContact(TestData.testScannedContact) }

        assertEquals(1, emittedEffects.size)
        assertEquals(MainEffect.ShowMessage("Contact Added"), emittedEffects.first())
    }

    @Test
    fun scanComplete_createContact_throwsError() = runTest {
        coEvery { mockSaveContact(TestData.testScannedContact) } throws TestData.sqlException
        every { mockErrorMsgResolver.resolve(TestData.sqlException.message) } returns "Database error"

        mainViewModel.setEvent(MainEvent.OnQrScanComplete(TestData.testScannedContact))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockSaveContact(TestData.testScannedContact) }

        assertEquals(1, emittedEffects.size)
        assertEquals(MainEffect.ShowMessage("Database error"), emittedEffects.first())
    }

    @Test
    fun scanCanceled_showsMessage() = runTest {
        val message = "Scan canceled"
        every { mockErrorMsgResolver.resolve(message) } returns message

        mainViewModel.setEvent(MainEvent.OnQrScanCanceled(message))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockErrorMsgResolver.resolve(message) }

        assertEquals(1, emittedEffects.size)
        assertEquals(MainEffect.ShowMessage(message), emittedEffects.first())
    }

    @Test
    fun scanFailed_showsMessage() = runTest {
        val message = "Scan failed"
        every { mockErrorMsgResolver.resolve(message) } returns message

        mainViewModel.setEvent(MainEvent.OnQrScanFail(message))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockErrorMsgResolver.resolve(message) }

        assertEquals(1, emittedEffects.size)
        assertEquals(MainEffect.ShowMessage(message), emittedEffects.first())
    }
}

package dev.arch3rtemp.contactexchange.presentation.ui.create

import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.usecase.SaveContactUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.ValidateContactUseCase
import dev.arch3rtemp.tests.coroutines.FlowTestObserver
import dev.arch3rtemp.tests.coroutines.MainCoroutinesRule
import dev.arch3rtemp.ui.util.ErrorMsgResolver
import dev.arch3rtemp.ui.util.StringResourceManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CreateCardViewModelTest {

    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @MockK
    private lateinit var mockSaveContact: SaveContactUseCase

    @MockK
    private lateinit var mockValidateContact: ValidateContactUseCase

    @MockK
    private lateinit var mockResourceManager: StringResourceManager

    @MockK
    private lateinit var mockErrorMsgResolver: ErrorMsgResolver

    @InjectMockKs
    private lateinit var createCardViewModel: CreateCardViewModel

    private lateinit var effectObserver: FlowTestObserver<CreateCardEffect>

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)

        runTest {
            effectObserver = FlowTestObserver<CreateCardEffect>(this, createCardViewModel.effect)
        }
    }

    @AfterTest
    fun tearDown() {
        effectObserver.cancel()
    }

    @Test
    fun onCreateButtonPress_cardCreatedSuccessfully() = runTest {
        coEvery { mockSaveContact(TestData.testMyContact) } returns Unit
        every { mockValidateContact(TestData.testMyContact) } returns true

        createCardViewModel.setEvent(CreateCardEvent.OnCreateButtonPress(TestData.testMyContact))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockSaveContact(TestData.testMyContact) }
        verify(exactly = 1) { mockValidateContact(TestData.testMyContact) }

        assertEquals(1, effectObserver.values.size)
        assertEquals(CreateCardEffect.NavigateUp, effectObserver.values[0])
    }

    @Test
    fun onCreateButtonPress_failsToCreateCard() = runTest {
        val message = "Database error"

        coEvery { mockSaveContact(TestData.testMyContact) } throws TestData.sqlException
        every { mockValidateContact(TestData.testMyContact) } returns true
        every { mockErrorMsgResolver.resolve(TestData.sqlException.message) } returns message

        createCardViewModel.setEvent(CreateCardEvent.OnCreateButtonPress(TestData.testMyContact))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockSaveContact(TestData.testMyContact) }
        verify(exactly = 1) { mockValidateContact(TestData.testMyContact) }

        assertEquals(1, effectObserver.values.size)
        assertEquals(CreateCardEffect.ShowError(message), effectObserver.values[0])
    }

    @Test
    fun onCreateButtonPress_invalidCard() = runTest {
        val message = "Please fill in all fields"

        coEvery { mockSaveContact(Contact()) } throws TestData.sqlException
        every { mockValidateContact(Contact()) } returns false
        every { mockResourceManager.string(R.string.msg_all_fields_required) } returns message

        createCardViewModel.setEvent(CreateCardEvent.OnCreateButtonPress(Contact()))

        advanceUntilIdle()

        coVerify(exactly = 0) { mockSaveContact(Contact()) }
        verify(exactly = 1) { mockValidateContact(Contact()) }
        verify(exactly = 1) { mockResourceManager.string(R.string.msg_all_fields_required) }

        assertEquals(1, effectObserver.values.size)
        assertEquals(CreateCardEffect.ShowError(message), effectObserver.values[0])
    }
}

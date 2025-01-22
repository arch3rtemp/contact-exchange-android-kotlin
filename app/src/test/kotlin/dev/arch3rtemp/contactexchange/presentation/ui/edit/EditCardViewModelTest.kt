package dev.arch3rtemp.contactexchange.presentation.ui.edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.usecase.GetContactByIdUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.UpdateContactUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.ValidateContactUseCase
import dev.arch3rtemp.contactexchange.presentation.mapper.ContactUiMapper
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
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class EditCardViewModelTest {

    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var mockGetContactById: GetContactByIdUseCase

    @MockK
    private lateinit var mockUpdateContact: UpdateContactUseCase

    @MockK
    private lateinit var mockValidateContact: ValidateContactUseCase

    @MockK
    private lateinit var mockMapper: ContactUiMapper

    @MockK
    private lateinit var mockResourceManager: StringResourceManager

    @MockK
    private lateinit var mockErrorMsgResolver: ErrorMsgResolver

    @InjectMockKs
    private lateinit var editCardViewModel: EditCardViewModel

    private val observedStates = mutableListOf<EditCardState>()

    private val stateObserver: (EditCardState) -> Unit = { state: EditCardState -> observedStates.add(state) }

    private lateinit var effectObserver: FlowTestObserver<EditCardEffect>

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)

        editCardViewModel.state.observeForever(stateObserver)
        runTest {
            effectObserver = FlowTestObserver<EditCardEffect>(this, editCardViewModel.effect)
        }
    }

    @AfterTest
    fun tearDown() {
        editCardViewModel.state.removeObserver(stateObserver)
        observedStates.clear()
        effectObserver.cancel()
    }

    @Test
    fun onCardLoad_getsCardSuccessfully() = runTest {
        coEvery { mockGetContactById(TestData.testMyContact.id) } returns TestData.testMyContact
        every { mockMapper.toUiModel(TestData.testMyContact) } returns TestData.testMyContactUi

        editCardViewModel.setEvent(EditCardEvent.OnCardLoad(TestData.testMyContact.id))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockGetContactById(TestData.testMyContact.id) }
        verify(exactly = 1) { mockMapper.toUiModel(TestData.testMyContact) }

        assertEquals(3, observedStates.size)
        assertEquals(EditCardState(ViewState.Idle), observedStates[0])
        assertEquals(EditCardState(ViewState.Loading), observedStates[1])
        assertEquals(EditCardState(ViewState.Success(TestData.testMyContactUi)), observedStates[2])
    }

    @Test
    fun onCardLoad_failsToGetCard() = runTest {
        coEvery { mockGetContactById(TestData.testMyContact.id) } throws TestData.sqlException
        every { mockErrorMsgResolver.resolve(any()) } returns "Database error"

        editCardViewModel.setEvent(EditCardEvent.OnCardLoad(TestData.testMyContact.id))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockGetContactById(TestData.testMyContact.id) }
        verify(exactly = 1) { mockErrorMsgResolver.resolve(any()) }

        assertEquals(3, observedStates.size)
        assertEquals(EditCardState(ViewState.Idle), observedStates[0])
        assertEquals(EditCardState(ViewState.Loading), observedStates[1])
        assertEquals(EditCardState(ViewState.Error), observedStates[2])

        assertEquals(1, effectObserver.values.size)
        assertEquals(EditCardEffect.ShowError("Database error"), effectObserver.values[0])
    }

    @Test
    fun onUpdateButtonPress_cardUpdatedSuccessfully() = runTest {
        coEvery { mockGetContactById(TestData.testMyContact.id) } returns TestData.testMyContact
        every { mockMapper.toUiModel(TestData.testMyContact) } returns TestData.testMyContactUi

        coEvery { mockUpdateContact(TestData.testMyContact, TestData.mergedContact) } returns Unit
        every { mockValidateContact(TestData.mergedContact) } returns true
        every { mockMapper.fromUiModel(TestData.testMyContactUi) } returns TestData.testMyContact

        // Load card state
        editCardViewModel.setEvent(EditCardEvent.OnCardLoad(TestData.testMyContact.id))

        advanceUntilIdle()

        // Test card edit
        editCardViewModel.setEvent(EditCardEvent.OnUpdateButtonPress(TestData.mergedContact))

        advanceUntilIdle()

        verify(exactly = 1) { mockValidateContact(TestData.mergedContact) }
        coVerify(exactly = 1) { mockUpdateContact(TestData.testMyContact, TestData.mergedContact) }

        assertEquals(1, effectObserver.values.size)
        assertEquals(EditCardEffect.NavigateUp, effectObserver.values[0])
    }

    @Test
    fun onUpdateButtonPress_failsToUpdateCard() = runTest {
        val message = "Database error"

        coEvery { mockGetContactById(TestData.testMyContact.id) } returns TestData.testMyContact
        every { mockMapper.toUiModel(TestData.testMyContact) } returns TestData.testMyContactUi

        coEvery { mockUpdateContact(TestData.testMyContact, TestData.mergedContact) } throws TestData.sqlException
        every { mockValidateContact(TestData.mergedContact) } returns true
        every { mockErrorMsgResolver.resolve(TestData.sqlException.message) } returns message
        every { mockMapper.fromUiModel(TestData.testMyContactUi) } returns TestData.testMyContact

        // Load card state
        editCardViewModel.setEvent(EditCardEvent.OnCardLoad(TestData.testMyContact.id))

        advanceUntilIdle()

        // Test card edit
        editCardViewModel.setEvent(EditCardEvent.OnUpdateButtonPress(TestData.mergedContact))

        advanceUntilIdle()

        verify(exactly = 1) { mockValidateContact(TestData.mergedContact) }
        coVerify(exactly = 1) { mockUpdateContact(TestData.testMyContact, TestData.mergedContact) }

        assertEquals(1, effectObserver.values.size)
        assertEquals(EditCardEffect.ShowError(message), effectObserver.values[0])
    }

    @Test
    fun onUpdateButtonPress_invalidCard() = runTest {
        val message = "Please fill in all fields"

        coEvery { mockGetContactById(TestData.testMyContact.id) } returns TestData.testMyContact
        every { mockMapper.toUiModel(TestData.testMyContact) } returns TestData.testMyContactUi

        coEvery { mockUpdateContact(TestData.testMyContact, Contact()) } throws TestData.sqlException
        every { mockValidateContact(Contact()) } returns false
        every { mockResourceManager.string(R.string.msg_all_fields_required) } returns message

        editCardViewModel.setEvent(EditCardEvent.OnUpdateButtonPress(Contact()))

        advanceUntilIdle()

        coVerify(exactly = 0) { mockUpdateContact(TestData.testMyContact, Contact()) }
        verify(exactly = 1) { mockValidateContact(Contact()) }
        verify(exactly = 1) { mockResourceManager.string(R.string.msg_all_fields_required) }

        assertEquals(1, effectObserver.values.size)
        assertEquals(EditCardEffect.ShowError(message), effectObserver.values[0])
    }
}

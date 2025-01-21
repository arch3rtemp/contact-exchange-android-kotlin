package dev.arch3rtemp.contactexchange.presentation.ui.card

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.coroutines.MainCoroutinesRule
import dev.arch3rtemp.contactexchange.domain.usecase.DeleteContactUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.GetContactByIdUseCase
import dev.arch3rtemp.contactexchange.presentation.mapper.ContactUiMapper
import dev.arch3rtemp.ui.util.ErrorMsgResolver
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
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
class CardViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @MockK
    private lateinit var mockGetContactById: GetContactByIdUseCase

    @MockK
    private lateinit var mockDeleteContact: DeleteContactUseCase

    @MockK
    private lateinit var mockMapper: ContactUiMapper

    @MockK
    private lateinit var mockErrorMsgResolver: ErrorMsgResolver

    @InjectMockKs
    private lateinit var cardViewModel: CardViewModel

    private val observedStates = mutableListOf<CardState>()

    private val stateObserver: (CardState) -> Unit = { state: CardState -> observedStates.add(state) }

    private val emittedEffects = mutableListOf<CardEffect>()

    private val effectJob = Job()

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)

        cardViewModel.state.observeForever(stateObserver)
        runTest {
            launch(effectJob) {
                cardViewModel.effect.collect { effect ->
                    emittedEffects.add(effect)
                }
            }
        }
    }

    @AfterTest
    fun tearDown() {
        cardViewModel.state.removeObserver(stateObserver)
        effectJob.cancel()
        observedStates.clear()
        emittedEffects.clear()
    }

    @Test
    fun onCardLoad_getsCardSuccessfully() = runTest {
        coEvery { mockGetContactById(TestData.testMyContact.id) } returns TestData.testMyContact
        every { mockMapper.toUiModel(TestData.testMyContact) } returns TestData.testMyContactUi

        cardViewModel.setEvent(CardEvent.OnCardLoad(TestData.testMyContact.id))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockGetContactById(TestData.testMyContact.id) }
        verify(exactly = 1) { mockMapper.toUiModel(TestData.testMyContact) }

        assertEquals(3, observedStates.size)
        assertEquals(CardState(ViewState.Idle), observedStates[0])
        assertEquals(CardState(ViewState.Loading), observedStates[1])
        assertEquals(CardState(ViewState.Success(TestData.testMyContactUi)), observedStates[2])
    }

    @Test
    fun onCardLoad_failsToGetCard() = runTest {
        coEvery { mockGetContactById(TestData.testMyContact.id) } throws TestData.sqlException
        every { mockErrorMsgResolver.resolve(any()) } returns "Database error"

        cardViewModel.setEvent(CardEvent.OnCardLoad(TestData.testMyContact.id))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockGetContactById(TestData.testMyContact.id) }
        verify(exactly = 1) { mockErrorMsgResolver.resolve(any()) }

        assertEquals(3, observedStates.size)
        assertEquals(CardState(ViewState.Idle), observedStates[0])
        assertEquals(CardState(ViewState.Loading), observedStates[1])
        assertEquals(CardState(ViewState.Error), observedStates[2])

        assertEquals(1, emittedEffects.size)
        assertEquals(CardEffect.ShowError("Database error"), emittedEffects[0])
    }

    @Test
    fun onCardDelete_deletesCardSuccessfully() = runTest {
        coEvery { mockDeleteContact(TestData.testScannedContact.id) } returns Unit

        cardViewModel.setEvent(CardEvent.OnCardDelete(TestData.testScannedContact.id))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockDeleteContact(TestData.testScannedContact.id) }

        assertEquals(1, emittedEffects.size)
        assertEquals(CardEffect.AnimateDeletion, emittedEffects[0])
    }

    @Test
    fun onCardDelete_failsToDeleteCard() = runTest {
        coEvery { mockDeleteContact(TestData.testScannedContact.id) } throws TestData.sqlException
        every { mockErrorMsgResolver.resolve(any()) } returns "Database error"
        cardViewModel.setEvent(CardEvent.OnCardDelete(TestData.testScannedContact.id))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockDeleteContact(TestData.testScannedContact.id) }

        assertEquals(1, emittedEffects.size)
        assertEquals(CardEffect.ShowError("Database error"), emittedEffects[0])
    }
}

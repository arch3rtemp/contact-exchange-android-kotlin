package dev.arch3rtemp.contactexchange.presentation.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.arch3rtemp.contactexchange.R
import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.coroutines.MainCoroutinesRule
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.contactexchange.domain.usecase.DeleteContactUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.FilterContactsUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.GetMyCardsUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.GetScannedContactsUseCase
import dev.arch3rtemp.contactexchange.domain.usecase.SaveContactUseCase
import dev.arch3rtemp.contactexchange.presentation.mapper.CardUiMapper
import dev.arch3rtemp.contactexchange.presentation.mapper.ContactUiMapper
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var mockGetMyCards: GetMyCardsUseCase

    @MockK
    private lateinit var mockGetScannedContacts: GetScannedContactsUseCase

    @MockK
    private lateinit var mockDeleteContact: DeleteContactUseCase

    @MockK
    private lateinit var mockSaveContact: SaveContactUseCase

    @MockK
    private lateinit var mockFilterContacts: FilterContactsUseCase

    @MockK
    private lateinit var mockContactUiMapper: ContactUiMapper

    @MockK
    private lateinit var mockCardUiMapper: CardUiMapper

    @MockK
    private lateinit var mockResourceManager: StringResourceManager

    @MockK
    private lateinit var mockErrorMsgResolver: ErrorMsgResolver

    @InjectMockKs
    private lateinit var homeViewModel: HomeViewModel

    private val observedStates = mutableListOf<HomeState>()
    private val stateObserver: (HomeState) -> Unit = { state: HomeState -> observedStates.add(state) }
    private val emittedEffects = mutableListOf<HomeEffect>()
    private val effectJob = Job()

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)

        homeViewModel.state.observeForever(stateObserver)
        runTest {
            launch(effectJob) {
                homeViewModel.effect.collect { effect ->
                    emittedEffects.add(effect)
                }
            }
        }
    }

    @AfterTest
    fun tearDown() {
        homeViewModel.state.removeObserver(stateObserver)
        effectJob.cancel()
        observedStates.clear()
        emittedEffects.clear()
    }

    @Test
    fun onContactsLoad_getsContactsSuccessfully() = runTest {
        coEvery { mockGetScannedContacts() } returns flowOf(TestData.testContacts)
        every { mockContactUiMapper.toUiList(TestData.testContacts) } returns TestData.testContactsUi
        every { mockFilterContacts.getFilteredContactsStream() } returns flowOf()

        homeViewModel.setEvent(HomeEvent.OnContactsLoad)

        advanceUntilIdle()

        coVerify(exactly = 1) { mockGetScannedContacts() }
        verify { mockContactUiMapper.toUiList(TestData.testContacts) }

        assertEquals(3, observedStates.size)
        assertEquals(HomeState(cardsState = CardState.Idle, contactsState = ContactState.Idle), observedStates[0])
        assertEquals(HomeState(cardsState = CardState.Idle, contactsState = ContactState.Loading), observedStates[1])
        assertEquals(HomeState(cardsState = CardState.Idle, contactsState = ContactState.Success(TestData.testContactsUi)), observedStates[2])

        assertEquals(0, emittedEffects.size)
    }

    @Test
    fun onContactsLoad_failsToGetContacts() = runTest {
        val messageEffect = "Database error"
        val messageState = "Data could not be loaded"

        coEvery { mockGetScannedContacts() } returns flow { throw TestData.sqlException }
        every { mockErrorMsgResolver.resolve(TestData.sqlException.message) } returns messageEffect
        every { mockResourceManager.string(R.string.msg_could_not_load_data) } returns messageState
        every { mockFilterContacts.getFilteredContactsStream() } returns flowOf()

        homeViewModel.setEvent(HomeEvent.OnContactsLoad)

        advanceUntilIdle()

        coVerify(exactly = 1) { mockGetScannedContacts() }

        assertEquals(3, observedStates.size)
        assertEquals(HomeState(cardsState = CardState.Idle, contactsState = ContactState.Idle), observedStates[0])
        assertEquals(HomeState(cardsState = CardState.Idle, contactsState = ContactState.Loading), observedStates[1])
        assertEquals(HomeState(cardsState = CardState.Idle, contactsState = ContactState.Error(messageState)), observedStates[2])

        assertEquals(1, emittedEffects.size)
        assertEquals(HomeEffect.ShowError(messageEffect), emittedEffects[0])
    }

    @Test
    fun onCardsLoad_getsCardsSuccessfully() = runTest {
        coEvery { mockGetMyCards() } returns flowOf(TestData.testContacts)
        every { mockCardUiMapper.toUiList(TestData.testContacts) } returns TestData.testCardsUi

        homeViewModel.setEvent(HomeEvent.OnCardsLoad)

        advanceUntilIdle()

        coVerify(exactly = 1) { mockGetMyCards() }
        verify { mockCardUiMapper.toUiList(TestData.testContacts) }

        assertEquals(3, observedStates.size)
        assertEquals(HomeState(cardsState = CardState.Idle, contactsState = ContactState.Idle), observedStates[0])
        assertEquals(HomeState(cardsState = CardState.Loading, contactsState = ContactState.Idle), observedStates[1])
        assertEquals(HomeState(cardsState = CardState.Success(TestData.testCardsUi), contactsState = ContactState.Idle), observedStates[2])

        assertEquals(0, emittedEffects.size)
    }

    @Test
    fun onCardsLoad_failsToGetCards() = runTest {
        val messageEffect = "Database error"
        val messageState = "Data could not be loaded"

        coEvery { mockGetMyCards() } returns flow { throw TestData.sqlException }
        every { mockErrorMsgResolver.resolve(TestData.sqlException.message) } returns messageEffect
        every { mockResourceManager.string(R.string.msg_could_not_load_data) } returns messageState

        homeViewModel.setEvent(HomeEvent.OnCardsLoad)

        advanceUntilIdle()

        coVerify(exactly = 1) { mockGetMyCards() }

        assertEquals(3, observedStates.size)
        assertEquals(HomeState(cardsState = CardState.Idle, contactsState = ContactState.Idle), observedStates[0])
        assertEquals(HomeState(cardsState = CardState.Loading, contactsState = ContactState.Idle), observedStates[1])
        assertEquals(HomeState(cardsState = CardState.Error(messageState), contactsState = ContactState.Idle), observedStates[2])

        assertEquals(1, emittedEffects.size)
        assertEquals(HomeEffect.ShowError(messageEffect), emittedEffects[0])
    }

    @Test
    fun onContactDelete_deletesContactSuccessfully() = runTest {
        coEvery { mockDeleteContact(TestData.testScannedContact.id) } returns Unit
        every { mockContactUiMapper.fromUiModel(TestData.testScannedContactUi) } returns TestData.testScannedContact

        homeViewModel.setEvent(HomeEvent.OnContactDelete(TestData.testScannedContactUi))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockDeleteContact(TestData.testScannedContact.id) }

        assertEquals(1, emittedEffects.size)
        assertEquals(HomeEffect.ShowUndo(TestData.testScannedContact), emittedEffects[0])
    }

    @Test
    fun onContactDelete_throwsError() = runTest {
        val message = "Database error"
        coEvery { mockDeleteContact(TestData.testScannedContact.id) } throws TestData.sqlException
        every { mockErrorMsgResolver.resolve(TestData.sqlException.message) } returns message

        homeViewModel.setEvent(HomeEvent.OnContactDelete(TestData.testScannedContactUi))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockDeleteContact(TestData.testScannedContact.id) }

        assertEquals(1, emittedEffects.size)
        assertEquals(HomeEffect.ShowError(message), emittedEffects[0])
    }

    @Test
    fun onContactSave_savesContactSuccessfully() = runTest {
        coEvery { mockSaveContact(TestData.testScannedContact) } returns Unit

        homeViewModel.setEvent(HomeEvent.OnContactSave(TestData.testScannedContact))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockSaveContact(TestData.testScannedContact) }
    }

    @Test
    fun onContactSave_throwsError() = runTest {
        val message = "Database error"

        coEvery { mockSaveContact(TestData.testScannedContact) } throws TestData.sqlException
        every { mockErrorMsgResolver.resolve(TestData.sqlException.message) } returns message

        homeViewModel.setEvent(HomeEvent.OnContactSave(TestData.testScannedContact))

        advanceUntilIdle()

        coVerify(exactly = 1) { mockSaveContact(TestData.testScannedContact) }

        assertEquals(1, emittedEffects.size)
        assertEquals(HomeEffect.ShowError(message), emittedEffects[0])
    }

    @Test
    fun onSearchTyped_filtersSuccessfully() = runTest {
        coEvery { mockGetScannedContacts() } returns flowOf(TestData.testContacts)
        every { mockContactUiMapper.toUiList(TestData.testContacts) } returns TestData.testContactsUi

        val filterFlow = MutableSharedFlow<List<Contact>>(replay = 1)
        val query = "Jane"
        val filteredContacts = listOf(TestData.testScannedContact)
        val filteredContactsUi = listOf(TestData.testScannedContactUi)

        every { mockFilterContacts.getFilteredContactsStream() } returns filterFlow
        coEvery { mockFilterContacts.invoke(query, TestData.testContacts) } coAnswers {
            filterFlow.emit(filteredContacts)
        }

        // Set initial data
        homeViewModel.setEvent(HomeEvent.OnContactsLoad)
        advanceUntilIdle()

        every { mockContactUiMapper.toUiList(filteredContacts) } returns filteredContactsUi

        // Filter contacts
        homeViewModel.setEvent(HomeEvent.OnSearchTyped(query))
        advanceUntilIdle()

        assertEquals(4, observedStates.size)
        assertEquals(HomeState(cardsState = CardState.Idle, contactsState = ContactState.Success(filteredContactsUi)), observedStates[3])
    }
}

package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.TestData
import dev.arch3rtemp.contactexchange.domain.model.Contact
import dev.arch3rtemp.tests.coroutines.TestDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FilterContactsUseCaseTest {

    private lateinit var filterContacts: FilterContactsUseCase
    private lateinit var testDispatcherProvider: TestDispatcherProvider

    @BeforeTest
    fun setUp() = runTest {
        testDispatcherProvider = TestDispatcherProvider()
        filterContacts = FilterContactsUseCase(testDispatcherProvider)
    }

    @Test
    fun checkWithDebounce_emitsOnTime() = runTest(testDispatcherProvider.testDispatcher) {
        val results = mutableListOf<List<Contact>>()

        // Start collecting the flow
        val job = filterContacts.getFilteredContactsStream()
            .onEach { results.add(it) }
            .launchIn(this)

        // 1) First emission
        filterContacts("First", emptyList())

        // Advance virtual time by only 299ms, just under the 300ms debounce
        advanceTimeBy(299L)
        runCurrent() // Process pending tasks

        // At this point, we expect NO emissions yet, because we haven't hit 300ms
        assertEquals(0, results.size)

        // 2) Advance 1 more millisecond to reach the 300ms debounce threshold
        advanceTimeBy(1L)
        runCurrent() // Process pending tasks

        // Now we expect 1 emission
        assertEquals(1, results.size)
        assertEquals(emptyList(), results.first())

        job.cancel()
    }

    @Test
    fun checkWithDebounceWithMultipleQueries_emitsOnTime() = runTest(testDispatcherProvider.testDispatcher) {
        val results = mutableListOf<List<Contact>>()

        // Start collecting the flow
        val job = filterContacts.getFilteredContactsStream()
            .onEach { results.add(it) }
            .launchIn(this)

        // First emission
        filterContacts("j", TestData.testContacts)

        // Advance virtual time by only 99ms
        advanceTimeBy(150L)
        runCurrent() // Process pending tasks

        // Second emission
        filterContacts("ja", TestData.testContacts)

        // Advance virtual time by only 99ms
        advanceTimeBy(150L)
        runCurrent() // Process pending tasks

        filterContacts("jan", TestData.testContacts)

        // Advance virtual time by only 99ms
        advanceTimeBy(300L)
        runCurrent() // Process pending tasks

        // Now we expect 1 emission
        assertEquals(1, results.size)
        assertEquals(listOf(TestData.testScannedContact), results.first())

        job.cancel()
    }

    @Test
    fun distinctUntilChanged_preventsDuplicateEmissions() = runTest(testDispatcherProvider.testDispatcher) {
        val results = mutableListOf<List<Contact>>()

        // Start collecting the flow
        val job = filterContacts.getFilteredContactsStream()
            .onEach { results.add(it) }
            .launchIn(this)

        // 1) Emit "query" + list
        filterContacts("jane", TestData.testContacts)
        advanceTimeBy(300L)
        runCurrent()
        assertEquals(1, results.size)

        // 2) Emit the same "query" + same list -> no new emission
        filterContacts("jane", TestData.testContacts)
        advanceTimeBy(300L)
        runCurrent()
        // Still only 1 emission because distinctUntilChanged() sees same query & list
        assertEquals(1, results.size)
        assertEquals(listOf(TestData.testScannedContact), results.first())

        job.cancel()
    }

    @Test
    fun filtersCards_correctly() = runTest(testDispatcherProvider.testDispatcher) {
        val results = mutableListOf<List<Contact>>()

        // Start collecting the flow
        val job = filterContacts.getFilteredContactsStream()
            .onEach { results.add(it) }
            .launchIn(this)

        // 1) Query = "ja" -> Should match "jane"
        filterContacts("ja", TestData.testContacts)

        // Advance time so the debounced emission goes through
        advanceTimeBy(300L)
        runCurrent()
        // We should have 1 emission
        assertEquals(1, results.size)

        // Check that it actually filtered as expected
        val filteredResult = results.first()
        assertEquals(1, filteredResult.size)
        assertEquals(TestData.testScannedContact, filteredResult.first())

        job.cancel()
    }

    @Test
    fun emptyQuery_returnsUnfiltered() = runTest(testDispatcherProvider.testDispatcher) {
        val results = mutableListOf<List<Contact>>()

        // Start collecting the flow
        val job = filterContacts.getFilteredContactsStream()
            .onEach { results.add(it) }
            .launchIn(this)

        // empty query
        filterContacts("", TestData.testContacts)

        // Advance time so the debounced emission goes through
        advanceUntilIdle()

        // We should have 1 emission
        assertEquals(1, results.size)
        assertEquals(TestData.testContacts, results.first())

        job.cancel()
    }

    @Test
    fun caseSensitiveQuery_filtersCorrectly() = runTest(testDispatcherProvider.testDispatcher) {
        val results = mutableListOf<List<Contact>>()

        // Start collecting the flow
        val job = filterContacts.getFilteredContactsStream()
            .onEach { results.add(it) }
            .launchIn(this)

        // case sensitive query
        filterContacts("JOHN", TestData.testContacts)

        advanceUntilIdle()

        assertEquals(1, results.size)
        assertEquals(TestData.testMyContact, results.first().first())

        job.cancel()
    }
}

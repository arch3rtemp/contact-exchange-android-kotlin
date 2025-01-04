package dev.arch3rtemp.contactexchange.domain.usecase

import dev.arch3rtemp.contactexchange.domain.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FilterContactsUseCase() {

    private val filterFlow = MutableSharedFlow<Pair<String, List<Contact>>>(replay = 1)

    /**
     * Initializes the use case, returning an observable that emits filtered contacts lists
     * whenever the query or unfiltered list changes.
     */
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun getFilteredContactsStream(): Flow<List<Contact>> {
        return filterFlow
            .debounce(DEBOUNCE_PERIOD_MS)
            .distinctUntilChanged()
            .flatMapLatest { pair ->
                flow {
                    emit(filterContacts(pair.first, pair.second))
                }.flowOn(Dispatchers.Default)
            }
    }

    /**
     * Triggers the filtering operation with the given query and unfiltered list of contacts.
     */
    suspend operator fun invoke(query: String, unfiltered: List<Contact>) {
        filterFlow.emit(Pair(query, unfiltered))
    }

    /**
     * Filters the list of contacts based on the query string.
     *
     * @param query      The search query.
     * @param unfiltered The list of unfiltered contacts.
     * @return A filtered list of contacts matching the query.
     */
    private fun filterContacts(query: String, unfiltered: List<Contact>): List<Contact> {
        if (query.isBlank()) return unfiltered

        val filterPattern = query.lowercase().trim()
        return unfiltered.filter { contact ->
            contact.name.lowercase().trim().contains(filterPattern)
        }
    }

    companion object {
        private const val DEBOUNCE_PERIOD_MS = 300L
    }
}

package dev.arch3rtemp.core_ui.view

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DebounceQueryTextListener(
    lifecycle: Lifecycle,
    private val debouncePeriod: Long = DEFAULT_DEBOUNCE_PERIOD,
    private val onDebounceQueryTextChange: (String) -> Unit
) : SearchView.OnQueryTextListener {

    private val coroutineScope = lifecycle.coroutineScope

    private var searchJob: Job? = null
    private var previousQuery: String? = DEFAULT_PREVIOUS_QUERY

    override fun onQueryTextSubmit(query: String?) = false

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != previousQuery) {
            searchJob?.cancel()
            searchJob = coroutineScope.launch {
                newText?.let {
                    delay(debouncePeriod)
                    onDebounceQueryTextChange(newText)
                }
            }
            previousQuery = newText
        }
        return false
    }

    companion object {
        private const val DEFAULT_DEBOUNCE_PERIOD = 300L
        private const val DEFAULT_PREVIOUS_QUERY = ""
    }
}
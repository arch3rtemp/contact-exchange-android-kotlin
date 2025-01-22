package dev.arch3rtemp.tests.coroutines

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope

class FlowTestObserver<T>(testScope: TestScope, flow: Flow<T>) {

    private val _values = mutableListOf<T>()
    val values: List<T> get() = _values
    val lastValue: T? get() = _values.lastOrNull()
    private val job = Job()

    init {
        testScope.launch(job) {
            flow.collect { _values.add(it) }
        }
    }

    fun cancel() {
        job.cancel()
        _values.clear()
    }
}

package dev.arch3rtemp.tests.coroutines

import androidx.lifecycle.Observer

class TestObserver<T> : Observer<T> {

    private val _values = mutableListOf<T>()
    val values: List<T> get() = _values
    val latestValue: T? get() = _values.lastOrNull()

    override fun onChanged(value: T) {
        _values.add(value)
    }

    fun clear() {
        _values.clear()
    }
}

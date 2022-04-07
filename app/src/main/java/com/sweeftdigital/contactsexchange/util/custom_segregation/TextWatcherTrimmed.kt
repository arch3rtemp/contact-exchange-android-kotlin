package com.sweeftdigital.contactsexchange.util.custom_segregation

import android.text.Editable
import android.text.TextWatcher

interface TextWatcherTrimmed : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?)
}
package com.sweeftdigital.contactsexchange.util

import com.sweeftdigital.contactsexchange.domain.models.Contact
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

internal fun Contact.dateToString(): String {
    val simpleDateFormat = SimpleDateFormat("dd MMM", Locale.US)
    return simpleDateFormat.format(createDate)
}
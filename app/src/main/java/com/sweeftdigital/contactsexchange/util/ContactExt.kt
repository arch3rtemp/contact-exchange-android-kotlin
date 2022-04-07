package com.sweeftdigital.contactsexchange.util

import com.sweeftdigital.contactsexchange.domain.models.Contact
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

internal fun Contact.dateToString(): String {
    val simpleDateFormat = SimpleDateFormat("dd MMM", Locale.US)
    return simpleDateFormat.format(createDate)
}

fun Contact.Companion.jsonToContact(jsonData: JSONObject): Contact {
    val firstName = jsonData.getString("name")
    val job = jsonData.getString("job")
    val position = jsonData.getString("position")
    val email = jsonData.getString("email")
    val phoneMobile = jsonData.getString("phoneMobile")
    val phoneOffice = jsonData.getString("phoneOffice")
    val createDate = Date()
    val color = jsonData.getInt("color")

    return Contact(
        name = firstName,
        job = job,
        position = position,
        email = email,
        phoneMobile = phoneMobile,
        phoneOffice = phoneOffice,
        createDate = createDate,
        color = color,
        isMy = Constants.NOT_MY_CARD
    )
}


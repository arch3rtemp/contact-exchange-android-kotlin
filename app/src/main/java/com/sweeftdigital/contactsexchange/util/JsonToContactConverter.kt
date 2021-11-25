package com.sweeftdigital.contactsexchange.util

import com.sweeftdigital.contactsexchange.domain.models.Contact
import org.json.JSONObject
import java.util.*

fun jsonToContact(jsonData: JSONObject): Contact {
    val firstName = jsonData.getString("firstName")
    val lastName = jsonData.getString("lastName")
    val job = jsonData.getString("job")
    val position = jsonData.getString("position")
    val email = jsonData.getString("email")
    val phoneMobile = jsonData.getString("phoneMobile")
    val phoneOffice = jsonData.getString("phoneOffice")
    val createDate = Date(jsonData.getLong("createDate"))
    val color = jsonData.getInt("color")

    return Contact(
        firstName = firstName,
        lastName = lastName,
        job = job,
        position = position,
        email = email,
        phoneMobile = phoneMobile,
        phoneOffice = phoneOffice,
        createDate = createDate,
        color = color,
        isMy = false
    )
}
package com.sweeftdigital.contactsexchange.util

import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.util.Constants.NOT_MY_CARD
import org.json.JSONObject
import java.util.*

fun jsonToContact(jsonData: JSONObject): Contact {
    val firstName = jsonData.getString("name")
    val job = jsonData.getString("job")
    val position = jsonData.getString("position")
    val email = jsonData.getString("email")
    val phoneMobile = jsonData.getString("phoneMobile")
    val phoneOffice = jsonData.getString("phoneOffice")
    val createDate = Date(jsonData.getLong("createDate"))
    val color = jsonData.getInt("color")

    return Contact(
        -1,
        name = firstName,
        job = job,
        position = position,
        email = email,
        phoneMobile = phoneMobile,
        phoneOffice = phoneOffice,
        createDate = createDate,
        color = color,
        isMy = NOT_MY_CARD
    )
}
package com.sweeftdigital.contactsexchange.util

import com.sweeftdigital.contactsexchange.app.db.models.ContactEntity
import org.json.JSONObject
import java.util.*

fun jsonToEntity(jsonData: JSONObject): ContactEntity {
    val firstName = jsonData.getString("firstName")
    val lastName = jsonData.getString("lastName")
    val job = jsonData.getString("job")
    val position = jsonData.getString("position")
    val email = jsonData.getString("email")
    val phoneMobile = jsonData.getString("phoneMobile")
    val phoneOffice = jsonData.getString("phoneOffice")
    val createDate = Date(jsonData.getLong("createDate"))
    val color = jsonData.getInt("color")

    return ContactEntity(
        firstName,
        lastName,
        job,
        position,
        email,
        phoneMobile,
        phoneOffice,
        createDate,
        color,
        false
    )
}
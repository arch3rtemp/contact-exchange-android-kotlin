package com.sweeftdigital.contactsexchange.domain.models

import java.util.*

data class Contact(
    val firstName: String,
    val lastName: String,
    val job: String,
    val position: String,
    val email: String,
    val phoneMobile: String,
    val phoneOffice: String,
    val createDate: Date,
    val color: Int,
    val isMy: Boolean
)

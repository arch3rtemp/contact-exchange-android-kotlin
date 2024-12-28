package dev.arch3rtemp.contactexchange.domain.model

import com.google.gson.Gson
import java.util.*

data class Contact(
    val id: Int = 0,
    val name: String = "",
    val job: String = "",
    val position: String = "",
    val email: String = "",
    val phoneMobile: String = "",
    val phoneOffice: String = "",
    val createDate: Date = Date(),
    val color: Int = 0,
    val isMy: ContactType = ContactType.NOT_MY_CARD
) {
    override fun toString(): String {
        return Gson().toJson(this)// TODO Replace with JSON
    }
}

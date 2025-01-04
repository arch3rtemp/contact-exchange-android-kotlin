package dev.arch3rtemp.contactexchange.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    val id: Int = 0,
    val name: String = "",
    val job: String = "",
    val position: String = "",
    val email: String = "",
    val phoneMobile: String = "",
    val phoneOffice: String = "",
    val createdAt: Long = -1,
    val color: Int = 0,
    val isMy: Boolean = false
) {
    fun isNotBlank(): Boolean {
        return name.isNotBlank() && job.isNotBlank() && position.isNotBlank() && email.isNotBlank() && phoneMobile.isNotBlank() && phoneOffice.isNotBlank()
    }
}

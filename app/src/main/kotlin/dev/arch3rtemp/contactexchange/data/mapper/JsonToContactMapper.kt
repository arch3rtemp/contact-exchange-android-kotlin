package dev.arch3rtemp.contactexchange.data.mapper

import dev.arch3rtemp.contactexchange.domain.model.Contact
import kotlinx.serialization.json.Json

class JsonToContactMapper() {

    private val json = Json { ignoreUnknownKeys = true }

    fun fromJson(rawCard: String): Contact {
        val parsedContact = json.decodeFromString<Contact>(rawCard)

        return parsedContact.copy(
            id = 0, // Scanned contacts should have no ID; Room will generate one.
            isMy = false, // Scanned contacts are never the user's own contact
            createdAt = System.currentTimeMillis() // Set the scan timestamp
        )
    }

    fun toJson(contact: Contact): String =
        json.encodeToString(value = contact, serializer = Contact.serializer())

}

package dev.arch3rtemp.contactexchange.presentation.mapper

import dev.arch3rtemp.contactexchange.domain.model.Contact
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ContactToJsonMapper {
    private val json = Json { ignoreUnknownKeys = true }

    fun toJson(contact: Contact): String {
        return json.encodeToString(contact)
    }
}

package dev.arch3rtemp.contactexchange.presentation.mapper

import dev.arch3rtemp.contactexchange.presentation.model.ContactUi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ContactToJsonMapper {
    private val json = Json { ignoreUnknownKeys = true }

    fun toJson(contact: ContactUi): String {
        return json.encodeToString(contact)
    }
}

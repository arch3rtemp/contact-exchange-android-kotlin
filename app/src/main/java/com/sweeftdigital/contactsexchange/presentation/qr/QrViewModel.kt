package com.sweeftdigital.contactsexchange.presentation.qr

import androidx.lifecycle.ViewModel
import com.google.zxing.Result
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.domain.useCases.SaveContactUseCase
import com.sweeftdigital.contactsexchange.util.jsonToContact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class QrViewModel(
    private val saveContactUseCase: SaveContactUseCase
) : ViewModel() {

    fun handleResult(rawResult: Result) {
        try {
            val contact = jsonToContact(JSONObject(rawResult.text))
            CoroutineScope(IO).launch {
                createContact(contact)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    private suspend fun createContact(contact: Contact) {
        saveContactUseCase.start(contact)
    }
}
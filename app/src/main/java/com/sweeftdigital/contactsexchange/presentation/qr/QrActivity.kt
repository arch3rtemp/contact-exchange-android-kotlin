package com.sweeftdigital.contactsexchange.presentation.qr

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import com.sweeftdigital.contactsexchange.databinding.ActivityQrBinding
import com.sweeftdigital.contactsexchange.domain.models.Contact
import com.sweeftdigital.contactsexchange.util.jsonToContact
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.log

class QrActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private val viewModel by viewModel<QrViewModel>()
    private lateinit var binding: ActivityQrBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scannerView.apply {
            setResultHandler(this@QrActivity)
            startCamera()
        }
    }

    override fun handleResult(rawResult: Result?) {
        rawResult?.let {
            val jsonObj = JSONObject(rawResult.text)
            val contact = Contact.jsonToContact(jsonObj)
            viewModel.setEvent(QrEvent.OnQrScan(contact))
        }
        setResult(RESULT_OK)
        finish()
    }
}
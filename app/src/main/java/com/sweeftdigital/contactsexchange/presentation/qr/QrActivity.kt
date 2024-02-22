package com.sweeftdigital.contactsexchange.presentation.qr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import com.sweeftdigital.contactsexchange.data.mapper.jsonToContact
import com.sweeftdigital.contactsexchange.databinding.ActivityQrBinding
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        runOnUiThread {  }
    }

    override fun handleResult(rawResult: Result?) {
        rawResult?.let {
            val jsonObj = JSONObject(rawResult.text)
            val contact = jsonToContact(jsonObj)
            viewModel.setEvent(QrEvent.OnQrScan(contact))
        }
        setResult(RESULT_OK)
        finish()
    }
}
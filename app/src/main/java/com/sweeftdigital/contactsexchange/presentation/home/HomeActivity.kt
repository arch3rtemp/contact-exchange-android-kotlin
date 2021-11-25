package com.sweeftdigital.contactsexchange.presentation.home

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.sweeftdigital.contactsexchange.R
import com.sweeftdigital.contactsexchange.databinding.ActivityHomeBinding
import com.sweeftdigital.contactsexchange.presentation.qr.QrActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var binding: ActivityHomeBinding

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        viewModel.onPermissionResult(granted)
    }

    var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val toastText = resources.getString(R.string.contact_added)
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeLiveViewState()
        setListeners()
    }

    private fun setListeners() {
        binding.llQrScanner.setOnClickListener {
            requestPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun subscribeLiveViewState() {
        viewModel.liveViewState.observe(this) { state ->
            state.isCameraPermissionGranted?.let { granted ->
                if (granted) {
                    val intent = Intent(this, QrActivity::class.java)
                    resultLauncher.launch(intent)
                } else {
                    val toastText = resources.getString(R.string.permission_denied)
                    Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
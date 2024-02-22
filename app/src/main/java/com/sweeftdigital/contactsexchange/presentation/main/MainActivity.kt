package com.sweeftdigital.contactsexchange.presentation.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.sweeftdigital.contactsexchange.R
import com.sweeftdigital.contactsexchange.databinding.ActivityMainBinding
import com.sweeftdigital.contactsexchange.presentation.qr.QrActivity


class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    val binding: ActivityMainBinding get() = _binding
    private lateinit var navController: NavController

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                openCamera()
            } else {
                val toastText = resources.getString(R.string.permission_denied)
                Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
            }
        }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val toastText = resources.getString(R.string.contact_added)
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationWithToolbar()
        setListeners()
    }

    private fun initNavigationWithToolbar() {
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val title = when (destination.id) {
                R.id.homeFragment -> {
                    binding.llBack.visibility = View.INVISIBLE
                    resources.getString(R.string.home_cards)
                }
                else -> {
                    binding.llBack.visibility = View.VISIBLE
                    ""
                }
            }
            supportActionBar?.title = title
        }

//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setListeners() {
        with(binding) {
            llQrScanner.setOnClickListener {
                requestPermission()
            }
            llBack.setOnClickListener {
                navController.navigateUp()
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    if (imm.isActive) {
                        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
                    }
                }
            }
        }
    }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> {
                Snackbar
                    .make(binding.fragmentContainerView, getString(R.string.allow_camera), Snackbar.LENGTH_LONG)
                    .setAction("Settings") {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            data = Uri.fromParts("package", packageName, null)
                        }
                        startActivity(intent)
                    }
                    .show()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(this, QrActivity::class.java)
        resultLauncher.launch(intent)
    }
}